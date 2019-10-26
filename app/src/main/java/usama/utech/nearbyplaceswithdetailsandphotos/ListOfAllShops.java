package usama.utech.nearbyplaceswithdetailsandphotos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usama.utech.nearbyplaceswithdetailsandphotos.Adaptar.AllShopsListAdaptar;
import usama.utech.nearbyplaceswithdetailsandphotos.model.Photo;
import usama.utech.nearbyplaceswithdetailsandphotos.model.Place;
import usama.utech.nearbyplaceswithdetailsandphotos.model.PlaceDetails;

public class ListOfAllShops extends AppCompatActivity {
    String type = "vulcanizing";
    LatLng latLngnewCenter = new LatLng(9.848396, 126.045457);

    StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    ArrayList<PlaceDetails> placeDetailsArrayList = new ArrayList<>();
    ArrayList<Place> placeArrayList = new ArrayList<>();
    private DisplayMetrics dm;

    RecyclerView rec_view_list_shops;
    AllShopsListAdaptar allShopsListAdaptar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_all_shops);

        sb.append("location=" + latLngnewCenter.latitude + "," + latLngnewCenter.longitude);
        sb.append("&radius=25000");
        sb.append("&name=" + type);
        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.google_direction_api));


        dm = new DisplayMetrics();

        // Getting the screen display metrics
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        rec_view_list_shops = findViewById(R.id.rec_view_list_shops);


        rec_view_list_shops.setLayoutManager(new LinearLayoutManager(ListOfAllShops.this));
        allShopsListAdaptar = new AllShopsListAdaptar(ListOfAllShops.this, placeDetailsArrayList, placeArrayList, dm);
        rec_view_list_shops.setAdapter(allShopsListAdaptar);


        progressDialog = new ProgressDialog(ListOfAllShops.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();

        getAllShops(sb.toString());


    }


    void get_place_data(String placeId) {


        try {


            RequestQueue queue = Volley.newRequestQueue(ListOfAllShops.this);
            String key = "key=" + getResources().getString(R.string.google_direction_api);

            String testurl = new String("https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJy6GKbsQGBDMR1qgLVUtCMVs&fields=price_level,vicinity,formatted_address,opening_hours,name,rating,formatted_phone_number&key=AIzaSyC7aO2ri5wsZEBhI3iqm70A1-m7vTYmehg");

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

                            String address = "Nill", name = "Nill", vicinity = "Nill", mobile = "Nill", price_level = "Nill", rating = "Nill", opening_hours = "Nill";
                            try {

                                person = new JSONObject(response);

                                if (person.getJSONObject("result").has("formatted_address"))
                                    address = person.getJSONObject("result").getString("formatted_address");

                                if (person.getJSONObject("result").has("name"))
                                    name = person.getJSONObject("result").getString("name");

                                if (person.getJSONObject("result").has("vicinity"))
                                    vicinity = person.getJSONObject("result").getString("vicinity");

                                if (person.getJSONObject("result").has("formatted_phone_number"))
                                    mobile = person.getJSONObject("result").getString("formatted_phone_number");


                                if (person.getJSONObject("result").has("rating"))
                                    rating = person.getJSONObject("result").getString("rating");

                                if (person.getJSONObject("result").has("opening_hours")) {
                                    opening_hours = String.valueOf(person.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text").get(0));
                                    opening_hours = opening_hours.substring(7);
                                }

                                if (person.getJSONObject("result").has("price_level")) {
                                    price_level = person.getJSONObject("result").getString("price_level");
                                }

                                PlaceDetails placeDetails = new PlaceDetails(price_level, vicinity, address, opening_hours, name, rating, mobile);


                                placeDetailsArrayList.add(placeDetails);

                                allShopsListAdaptar.notifyDataSetChanged();

                                System.out.println("myresponce " + person);

                                System.out.println("Place is =" + price_level + vicinity + address + opening_hours + name + rating + mobile);

                            } catch (JSONException e) {
                                System.out.println("Place is error " + e.getMessage());
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("distance is error" + error);
                    progressDialog.dismiss();
                }
            });
            queue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListOfAllShops.this, MainActivity.class));
        finish();
    }

    void getAllShops(String url) {

        try {
            RequestQueue queue = Volley.newRequestQueue(ListOfAllShops.this);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject person = null;
                            JSONArray jPlaces = null;

                            String address = "Nill", name = "Nill", vicinity = "Nill", mobile = "Nill", price_level = "Nill", rating = "Nill", opening_hours = "Nill";
                            try {

                                person = new JSONObject(response);

                                jPlaces = person.getJSONArray("results");

                                for (int i = 0; i < jPlaces.length(); i++) {

                                    Place place = getPlace((JSONObject) jPlaces.get(i));

                                    if (place.mPhotos.length > 0)
                                        System.out.println("photo is: " + place.mPhotos[0].mPhotoReference);

                                    get_place_data(place.mPlaceId);

                                    placeArrayList.add(place);

                                    if (placeDetailsArrayList.size() > 0)
                                        System.out.println("photo is p: " + placeDetailsArrayList.get(0).getFormatted_phone_number());

                                    allShopsListAdaptar.notifyDataSetChanged();

                                }

                                progressDialog.dismiss();

                                System.out.println("Place is =" + price_level + vicinity + address + opening_hours + name + rating + mobile);

                            } catch (JSONException e) {
                                System.out.println("Place is error " + e.getMessage());
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    System.out.println("distance is error" + error);
                }
            });
            queue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Place getPlace(JSONObject jPlace) {

        Place place = new Place();


        try {
            // Extracting Place name, if available


            if (!jPlace.isNull("place_id")) {
                place.mPlaceId = jPlace.getString("place_id");
            }

            if (!jPlace.isNull("name")) {
                place.mPlaceName = jPlace.getString("name");
            }


            // Extracting Place Vicinity, if available
            if (!jPlace.isNull("vicinity")) {
                place.mVicinity = jPlace.getString("vicinity");
            }

            if (!jPlace.isNull("photos")) {
                JSONArray photos = jPlace.getJSONArray("photos");
                place.mPhotos = new Photo[photos.length()];
                for (int i = 0; i < photos.length(); i++) {
                    place.mPhotos[i] = new Photo();
                    place.mPhotos[i].mWidth = ((JSONObject) photos.get(i)).getInt("width");
                    place.mPhotos[i].mHeight = ((JSONObject) photos.get(i)).getInt("height");
                    place.mPhotos[i].mPhotoReference = ((JSONObject) photos.get(i)).getString("photo_reference");
                    JSONArray attributions = ((JSONObject) photos.get(i)).getJSONArray("html_attributions");
                    place.mPhotos[i].mAttributions = new Attribution[attributions.length()];
                    for (int j = 0; j < attributions.length(); j++) {
                        place.mPhotos[i].mAttributions[j] = new Attribution();
                        place.mPhotos[i].mAttributions[j].mHtmlAttribution = attributions.getString(j);
                    }
                }
            }

            place.mLat = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
            place.mLng = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("EXCEPTION", e.toString());
        }
        return place;
    }


}
