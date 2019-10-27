package usama.utech.nearbyplaceswithdetailsandphotos;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import usama.utech.nearbyplaceswithdetailsandphotos.Common.Common;
import usama.utech.nearbyplaceswithdetailsandphotos.model.Place;
import usama.utech.nearbyplaceswithdetailsandphotos.remote.IGoogleAPI;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    // Specifies the drawMarker() to draw the marker with default color
    private static final float UNDEFINED_COLOR = -1;
    // GoogleMap
    GoogleMap mGoogleMap;
    // Spinner in which the location types are stored
    Spinner mSprPlaceType;
    // A button to find the near by places
    Button mBtnFind = null;
    // Stores near by places
    Place[] mPlaces = null;
    // A String array containing place types sent to Google Place service
    String[] mPlaceType = null;
    // A String array containing place types displayed to user
    String[] mPlaceTypeName = null;
    // The location at which user touches the Google Map
    LatLng mLocation = null;
    // Links marker id and place object
    HashMap<String, Place> mHMReference = new HashMap<String, Place>();
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private Toolbar toolbar;
    private String response;
    private String parsedDistance;
    IGoogleAPI mService;

    private static final LatLngBounds Siargao_Island_BOUNDS = new LatLngBounds(new LatLng(9.755424, 126.054844),
            new LatLng(10.051587, 126.049695));
    private LocationManager locationManager;
    public static final int REQUEST_LOCATION_CODE = 99;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 22;

    SearchView search_bar_for_service_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search_bar_for_service_search  = findViewById(R.id.search_bar_for_service_search);

        if (!isGooglePlayServicesAvailable(this)) {
            Toast.makeText(this, "Play services not updated, please update them in order to use this app", Toast.LENGTH_SHORT).show();
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            checkLocationPermission() ;
           checkLocation();//check whether location service is enable or not in your  phone



        }


        mService = Common.getGoogleAPI();

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        dl.addDrawerListener(t);
        t.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
//                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_gallery:
                        startActivity(new Intent(getApplicationContext(), ShopsMuncipilityList.class));
                        finish();
                        break;
                    case R.id.nav_share:
                        startActivity(new Intent(getApplicationContext(), AboutApp.class));
                        finish();
                        break;
                    case R.id.nav_send:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        finish();
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });


        search_bar_for_service_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                newText = newText.toLowerCase();

                if (!newText.equals("")) {


                    search_for_service(newText);




                } else {
                    Toast.makeText(MainActivity.this, "Enter Text First", Toast.LENGTH_SHORT).show();
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });


        // Array of place types
        mPlaceType = getResources().getStringArray(R.array.place_type);

        // Array of place type names
        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

        // Creating an array adapter with an array of Place types
        // to populate the spinner


        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else {
            // Google Play Services are available

            // Getting reference to the SupportMapFragment
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);


            // Getting Google Map
            mapFragment.getMapAsync(MainActivity.this);


            // Handling screen rotation
            if (savedInstanceState != null) {

                // Removes all the existing links from marker id to place object
                mHMReference.clear();

                //If near by places are already saved
                if (savedInstanceState.containsKey("places")) {

                    // Retrieving the array of place objects
                    mPlaces = (Place[]) savedInstanceState.getParcelableArray("places");

                    // Traversing through each near by place object
                    for (int i = 0; i < mPlaces.length; i++) {

                        // Getting latitude and longitude of the i-th place
                        LatLng point = new LatLng(Double.parseDouble(mPlaces[i].mLat),
                                Double.parseDouble(mPlaces[i].mLng));

                        // Drawing the marker corresponding to the i-th place
                        Marker m = drawMarker(point, UNDEFINED_COLOR);

                        // Linkng i-th place and its marker id
                        mHMReference.put(m.getId(), mPlaces[i]);
                    }

                }

                // If a touched location is already saved
                if (savedInstanceState.containsKey("location")) {

                    // Retrieving the touched location and setting in member variable
                    mLocation = (LatLng) savedInstanceState.getParcelable("location");

                    // Drawing a marker at the touched location
                    drawMarker(mLocation, BitmapDescriptorFactory.HUE_GREEN);
                }
            }

        }


    }








    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }



    public boolean checkLocationPermission() {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION_CODE);
                }
                return false;

            } else
                return true;

    }




    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }








    void startTask() {


        String type = "vulcanizing";

        if (mGoogleMap != null)
            mGoogleMap.clear();


        if (mLocation == null) {
            Toast.makeText(getBaseContext(), "Please mark a location", Toast.LENGTH_SHORT).show();
            return;
        }

        drawMarker(mLocation, BitmapDescriptorFactory.HUE_BLUE);

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLocation.latitude + "," + mLocation.longitude);
        sb.append("&radius=25000");
        sb.append("&name=" + type);
        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.google_direction_api));


        // Creating a new non-ui thread task to download Google place json data
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());
    }



    void search_for_service(String type){


            if (mGoogleMap != null)
                mGoogleMap.clear();


            if (mLocation == null) {
                Toast.makeText(getBaseContext(), "Please mark a location", Toast.LENGTH_SHORT).show();
                return;
            }

            drawMarker(mLocation, BitmapDescriptorFactory.HUE_BLUE);


        LatLng latLngnew = new LatLng(9.848396, 126.045457);

            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            sb.append("location=" + mLocation.latitude + "," + mLocation.longitude);
            sb.append("&radius=30000");
            sb.append("&name=" + type);
            sb.append("&sensor=true");
            sb.append("&key=" + getResources().getString(R.string.google_direction_api));






        try {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);




            StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject person = null;
                            JSONArray jPlaces = null;
                            try {

                                Place[] places = null;
                                PlaceJSONParser placeJsonParser = new PlaceJSONParser();


                                person = new JSONObject(response);
                                    /** Getting the parsed data as a List construct */

                                    System.out.println("json obj: " + person);
                                    places = placeJsonParser.parse(person);



                                mPlaces = places;
                                Common.placeArrayList_static = Arrays.asList(places);
                                if (Common.placeArrayList_static.size() != 0) {
                                    System.out.println("array is " + Common.placeArrayList_static.get(0).mPlaceName);

                                }
                                for (int i = 0; i < places.length; i++) {

                                    Place place = places[i];


                                    get_place_data(place.mPlaceId);

                                    //    Toast.makeText(MainActivity.this, "name: " + place.mPlaceName + " place id " + place.mPlaceId, Toast.LENGTH_SHORT).show();

                                    // Getting latitude of the place
                                    double lat = Double.parseDouble(place.mLat);

                                    // Getting longitude of the place
                                    double lng = Double.parseDouble(place.mLng);

                                    LatLng latLng = new LatLng(lat, lng);

                                    Marker m = drawMarker(latLng, UNDEFINED_COLOR);

                                    // Adding place reference to HashMap with marker id as HashMap key
                                    // to get its reference in infowindow click event listener
                                    mHMReference.put(m.getId(), place);

                                }
                                System.out.println("myresponce " + person);

                                System.out.println("Place is " );

                            } catch (JSONException e) {
                                System.out.println("Place is error " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("distance is error" + error);
                }
            });
            queue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }











    }


    /**
     * A callback function, executed on screen rotation
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Saving all the near by places objects
        if (mPlaces != null)
            outState.putParcelableArray("places", mPlaces);

        // Saving the touched location
        if (mLocation != null)
            outState.putParcelable("location", mLocation);

        super.onSaveInstanceState(outState);
    }


    /**
     * A method to download json data from argument url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);


            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        // Enabling MyLocation in Google Map
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng latLngnewCenter = new LatLng(9.848396, 126.045457);


        LatLng one = new LatLng(9.755424, 126.054844);
        LatLng two = new LatLng(10.051587, 126.049695);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mGoogleMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mGoogleMap.setMinZoomPreference(mGoogleMap.getCameraPosition().zoom);


//        mGoogleMap.setLatLngBoundsForCameraTarget(Siargao_Island_BOUNDS);

//
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngnewCenter,15.5f));


        // Map Click listener
        mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Clears all the existing markers
                mGoogleMap.clear();

                // Setting the touched location in member variable
                mLocation = point;

                // Drawing a marker at the touched location
                drawMarker(mLocation, BitmapDescriptorFactory.HUE_GREEN);

            }
        });

        // Marker click listener
        mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                // If touched at User input location
                if (!mHMReference.containsKey(marker.getId()))
                    return false;

                LatLng start = new LatLng(mLocation.latitude, mLocation.longitude);

                LatLng end = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

                Routing routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.DRIVING)
                        .withListener(new RoutingListener() {
                            @Override
                            public void onRoutingFailure(RouteException e) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onRoutingStart() {

                            }

                            @Override
                            public void onRoutingSuccess(ArrayList<Route> route, int position) {

                                Route route1 = route.get(0);
                                //  Toast.makeText(getApplicationContext(), "Route " + (1) + ": distance - " + route1.getDistanceValue() + "m : duration - " + (route1.getDurationValue() / 60), Toast.LENGTH_SHORT).show();

                                View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_for_distance_and_duration, null);

                                TextView duration = (TextView) dialogView.findViewById(R.id.txt_dialog_duration);
                                duration.setText("Duration : " + (route1.getDurationValue() / 60) + " min");

                                TextView distance = (TextView) dialogView.findViewById(R.id.txt_dialog_distance);
                                distance.setText("Distance : " + (route1.getDistanceValue() / 1000) + " km");

                                BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                                dialog.setContentView(dialogView);
                                dialog.show();


                                //add route(s) to the map.
//                                for (int i = 0; i <route.size(); i++) {
//
//
//
//                                    Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+"m : duration - "+ (route.get(i).getDurationValue()/60),Toast.LENGTH_SHORT).show();
//
//
//
//
//
//                                }


                            }

                            @Override
                            public void onRoutingCancelled() {
                                Toast.makeText(MainActivity.this, "cancelled", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .waypoints(start, end)
                        .key(getResources().getString(R.string.google_maps_key))
//                .alternativeRoutes(true)
                        .build();
                routing.execute();


                // Getting place object corresponding to the currently clicked Marker
                Place place = mHMReference.get(marker.getId());

                System.out.println("my data :" + place.mPlaceName);

                //Toast.makeText(MainActivity.this, "place data :"+place, Toast.LENGTH_SHORT).show();

                // Creating an instance of DisplayMetrics
                DisplayMetrics dm = new DisplayMetrics();

                // Getting the screen display metrics
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                // Creating a dialog fragment to display the photo
                PlaceDialogFragment dialogFragment = new PlaceDialogFragment(place, dm);

                // Getting a reference to Fragment Manager
                FragmentManager fm = getSupportFragmentManager();

                // Starting Fragment Transaction
                FragmentTransaction ft = fm.beginTransaction();

                // Adding the dialog fragment to the transaction
                ft.add(dialogFragment, "TAG");

                // Committing the fragment transaction
                ft.commit();

                return false;
            }
        });
    }


    private String downloadUrlForDistance(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            System.out.println("my as " + data);
            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    void get_place_data(String placeId) {


        try {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String key = "key=" + getResources().getString(R.string.google_direction_api);

            //  https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=price_level,vicinity,formatted_address,opening_hours,name,rating,formatted_phone_number&key=AIzaSyC7aO2ri5wsZEBhI3iqm70A1-m7vTYmehg

//            String url = "https://maps.googleapis.com/maps/api/directions/" +
//                    "json?origin=" + locationJsonObject.getString("origin") + "&destination=" + locationJsonObject.getString("destination") + "&mode=driving" +
//                    "&sensor=false&" + key;

            System.out.println("placeid " + placeId);

            String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeId + "&fields=price_level,vicinity,formatted_address,opening_hours,name,rating,formatted_phone_number&" + key;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject person = null;
                            JSONArray jPlaces = null;
                            try {

                                person = new JSONObject(response);


                                String name = person.getJSONObject("result").getString("formatted_address");
                                String email = person.getJSONObject("result").getString("name");

                                String home = person.getJSONObject("result").getString("vicinity");
                                String mobile = person.getJSONObject("result").getString("formatted_phone_number");


                                System.out.println("myresponce " + person);

                                System.out.println("Place is " + name);

                            } catch (JSONException e) {
                                System.out.println("Place is error " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("distance is error" + error);
                }
            });
            queue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getretrofit(String placeId) {
        try {
            String key = "key=" + getResources().getString(R.string.google_direction_api);

            String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=price_level,vicinity,formatted_address,opening_hours,name,rating,formatted_phone_number&" + key;

            mService.getPath(url)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                            try {


                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                System.out.println("myresponce " + jsonObject);


                                String name = jsonObject.getString("formatted_address");
                                System.out.println("myresponcedata is " + name);

//
//                                JSONArray routes = jsonObject.getJSONArray("routes");
//
//                                //after getting routes , just get first element of routes
//                                JSONObject object = routes.getJSONObject(0);
//                                //after getting first element we need to get array with legs
//                                JSONArray legs = object.getJSONArray("legs");
//                                //and get first elenment of legs array
//                                JSONObject legsObject = legs.getJSONObject(0);
//                                //get distance
//                                JSONObject distance = legsObject.getJSONObject("distance");
//                                txtDistance.setText(distance.getString("text"));
//                                //get time
//                                JSONObject time = legsObject.getJSONObject("duration");
//                                txtTime.setText(time.getString("text"));
//                                //get address
//                                String address = legsObject.getString("end_address");
//                                txtAddress.setText(address);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {

        }
    }


    private void LatlngCalc(JSONObject locationJsonObject) {
        try {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String key = "key=" + getResources().getString(R.string.google_direction_api);
            String url = "https://maps.googleapis.com/maps/api/directions/" +
                    "json?origin=" + locationJsonObject.getString("origin") + "&destination=" + locationJsonObject.getString("destination") + "&mode=driving" +
                    "&sensor=false&" + key;

            URL url1;

            url1 = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=33.589759,71.440228&destination=34.589759,70.440228&sensor=false&mode=driving&key=AIzaSyC7aO2ri5wsZEBhI3iqm70A1-m7vTYmehg");


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("distance is " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("distance is error" + error);
                }
            });
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Drawing marker at latLng with color
     */
    private Marker drawMarker(LatLng latLng, float color) {
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        if (color != UNDEFINED_COLOR)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));

        // Placing a marker on the touched position
        Marker m = mGoogleMap.addMarker(markerOptions);

        return m;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item)) {
            return true;
        }


        // Handle item selection

        switch (item.getItemId()) {
            case R.id.action_show_on_map:
                startTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * A class, to download Google Places
     */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                Log.d("URL:", url[0]);
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }


            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of ParserTask
            parserTask.execute(result);
        }

    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, Place[]> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected Place[] doInBackground(String... jsonData) {


            Place[] places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);
                /** Getting the parsed data as a List construct */

                System.out.println("json obj: " + jObject);
                places = placeJsonParser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }




        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(Place[] places) {

            mPlaces = places;
            Common.placeArrayList_static = Arrays.asList(places);
            if (Common.placeArrayList_static.size() != 0) {
                System.out.println("array is " + Common.placeArrayList_static.get(0).mPlaceName);

            }
            for (int i = 0; i < places.length; i++) {

                Place place = places[i];


                get_place_data(place.mPlaceId);

            //    Toast.makeText(MainActivity.this, "name: " + place.mPlaceName + " place id " + place.mPlaceId, Toast.LENGTH_SHORT).show();

                // Getting latitude of the place
                double lat = Double.parseDouble(place.mLat);

                // Getting longitude of the place
                double lng = Double.parseDouble(place.mLng);

                LatLng latLng = new LatLng(lat, lng);

                Marker m = drawMarker(latLng, UNDEFINED_COLOR);

                // Adding place reference to HashMap with marker id as HashMap key
                // to get its reference in infowindow click event listener
                mHMReference.put(m.getId(), place);

            }
        }

    }
}