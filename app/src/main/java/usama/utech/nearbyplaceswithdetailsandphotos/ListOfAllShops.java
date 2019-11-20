package usama.utech.nearbyplaceswithdetailsandphotos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

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


        dm = new DisplayMetrics();

        // Getting the screen display metrics
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        rec_view_list_shops = findViewById(R.id.rec_view_list_shops);


        rec_view_list_shops.setLayoutManager(new LinearLayoutManager(ListOfAllShops.this));
        allShopsListAdaptar = new AllShopsListAdaptar(ListOfAllShops.this, placeDetailsArrayList, placeArrayList, dm);
        rec_view_list_shops.setAdapter(allShopsListAdaptar);


//        progressDialog = new ProgressDialog(ListOfAllShops.this, R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Loading Data ...");
//        progressDialog.show();


        sb.append("location=" + latLngnewCenter.latitude + "," + latLngnewCenter.longitude);
        sb.append("&radius=25000");


        if (getIntent().getStringExtra("type") != null) {
            sb.append("&name=" + type + " " + getIntent().getStringExtra("type"));

            switch (getIntent().getStringExtra("type")) {

                case "San Benito": {
                    adddataSanBenito();
                    break;
                }
                case "Del Carmen": {
                    adddatadelcarmen();
                    break;
                }
                case "Burgos": {
                    adddataburgos();
                    break;
                }
                case "Dapa": {
                    adddatadapa();
                    break;
                }
                case "Pilar": {
                    adddatapilirs();
                    break;
                }
                case "Santa Monica": {
                    adddataSantamonica();
                    break;
                }
                case "San Isidro": {
                    adddataSANISIDRO();
                    break;
                }
                case "all": {

                    addAllData();
                    break;
                }
                case "General Luna": {
                    adddatagenral();
                    break;
                }
            }


        } else {

            sb.append("&name=" + type);
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.google_direction_api));

        //  getAllShops(sb.toString());


    }


    void addAllData() {

        adddataSanBenito();
        adddataburgos();
        adddatadapa();
        adddatadelcarmen();
        adddatapilirs();
        adddataSANISIDRO();
        adddataSantamonica();
    }

    void adddataSanBenito() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Cherry Mantilla", "Talisay, San Benito", "7:00am-6:00pm", "CM Motor Parts and Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Motor wiring\n" +
                "•Battery Charging\n" +
                "•Change tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•OverWholing\n" +
                "•Motorparts and Accessories", "091005695334", R.drawable.cm));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Mendavia", "P-4, Talisay, San Benito ", "7:00am-6:00pm", "Mendavia  Vulcanizing Shop ", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure", "09489745412", R.drawable.mendavia_));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Loloy Polican", "San Juan, San Benito", "7:00am-6:00pm", "Polican  Vulcanizing Shop", "\n•Vulcanize\t\n" +
                "•Tire Air Pressure\t          \n" +
                "•Battery Charging", "09489745412", R.drawable.polican));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Bernie ", "Talisay, San Benito ", "7:00am-6:00pm", "Bernie Tire Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure", "09104342823  ", R.drawable.bernie_));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Brendo ", "Talisay, San Benito", "7:00am-6:00pm", "Brendo Vulcanizing Shop", "\n•Interior\t\n" +
                "•Tire ", "091005695334", R.drawable.brendo_));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddatadelcarmen() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Debora Tesado", "Esperanza, Del Carmen ", "7:00am-6:00pm", "Debora  Vulcanizing Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure\n" +
                "•Tubeless Repair", "0945685057 ", R.drawable.debora__));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Marniel Gubaton", "Cancohoy, Del Carmen  ", "7:00am-6:00pm", "Motorparts Accessories ", "\n•Tubeless Repair\n" +
                "•Tire Air Pressure\n" +
                "•Motorparts and Accessories", "09485134929 ", R.drawable.motorparts));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Joseph Lasala", "Cancohoy, Del Carmen ", "7:00am-6:00pm", "Lasala Vulcanizing Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure\n" +
                "•Change tire/Interior\n" +
                "•Tubeless Repair ", "09101733903 ", R.drawable.lasala));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Susan Pobe ", "Cancohoy, Del Carmen", "7:00am-6:00pm", "Pobe Vulcanizing Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure\n" +
                "Change tire/Interior", "09101733903   ", R.drawable.pobe));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Renaldo Alegre", " P-2 Cabugao, Del Carmen ", "7:00am-6:00pm", "Alegre Vulcanizing Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure\n" +
                "•Change tire/Interior", "0945685057 ", R.drawable.alegre));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Moises Tesurna", " P-3 Bitoon, Del Carmen  ", "7:00am-6:00pm", "Tesurna Vulcanizing Shop", "\n•Vulcanize\t \n" +
                "•Tire Air Pressure\n" +
                "•Welding\n" +
                "•Change tire/ Interior", "09655427036  ", R.drawable.tesurna));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddataburgos() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Ankian Ngo", "Burgos", "7:00am-6:00pm", "Ngo Vulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "09086801757", R.drawable.ngo));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Vinson", "Burgos ", "7:00am-6:00pm", "Vinson Motor parts and Vulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Motor Parts Accessories", "09086801757", R.drawable.vinson));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddatadapa() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Petronio Dulguime", "P-1, Buyo-Buyo , Dapa ", "7:00am-6:00pm", "Dulguime  Vulcanizing  Shop", "\n•Interior/Tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "09489745412  ", R.drawable.dulguime));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Joseph Lasala", "P-4, Lubogon, Dapa", "7:00am-6:00pm", "Lasala Vulcanzing Shop ", "\n•Tubeless Repair\n" +
                "•Interior/Tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "09489745412  ", R.drawable.lasala));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Nina Abacon", "Brgy. 13. Dapa ", "7:00am-6:00pm", "NMC Motoparts and Accessories", "\n•Motor parts\n" +
                "•Accessories\n" +
                "•Change Tire/ Interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "09489745412  ", R.drawable.nmc));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Kenneth Tiu ", "Brgy. 11, Dapa", "7:00am-6:00pm", "Tiu Motorparts  and Vulcanizing Shop", "•\nMotorparts  and Accessories\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Tubeless Repair\n" +
                "•Motor wiring\n" +
                "•Battery Charging\n" +
                "•Change tyre\n" +
                "•OverWholing", "09300523210    ", R.drawable.tiu));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Jerome Comandante", " Brgy. 12 Catabaan, Dapa", "7:00am-6:00pm", "MotorParts and Vulanizing Shop", "\n•Change tire\n" +
                "•Lock ring tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "091005695334  ", R.drawable.motorparts));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Castrol, Carlo", " Dapa ", "7:00am-6:00pm", "Castrol MotorParts and Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Motor wiring\n" +
                "•Battery Charging\n" +
                "•Change tire\n" +
                "•Lock ring tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Pubrication\n" +
                "•OverWholing\n" +
                "•Motorparts and Accessories", "091005695334   ", R.drawable.castrol));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Martin Aldora  ", "Osmeña, Dapa ", "7:00am-6:00pm", "Aldora Vulcanizing Shop", "\n•Tire Pressure\n" +
                "•Vulcanize\n" +
                "•Rim Alignment", "0907084476    ", R.drawable.ic_launcher));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Isaac", " Brgy 7 Dapa ", "7:00am-6:00pm", "Isaac Spare Sparts Shop", "\n•Spare Sparts\n" +
                "•Motor parts and Accessories", "091005695334  ", R.drawable.isaac));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Isaac", " Brgy. 7, Dapa", "7:00am-6:00pm", "Isaac Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Motor wiring\n" +
                "•Battery Charging\n" +
                "•Change tyre\n" +
                "•Lock ring tyre\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Pubrication\n" +
                "•OverWholing\n" +
                "•Motorparts and Accessories", "091005695334   ", R.drawable.isaac_vulcanizing));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddatapilirs() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Longlong Tesiorna", "Maasin, Pilar ", "7:00am-6:00pm", "Tesiorna Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "091005695334", R.drawable.tesiorna));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Roma Nier ", "Mabini, Pilar", "7:00am-6:00pm", "Nier Motor parts and Vulcanizing Shop ", "\n•Tubeless Repair\n" +
                "•Change tire/interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Motor parts Accessories", "091005695334   ", R.drawable.nier));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Elisita Salvaloza", "San Roque, Pilar", "7:00am-6:00pm", "SalvalozaVulcanizing Shop ", "\n•Tubeless Repair\n" +
                "•Change tire/interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Motor parts Accessories", "09381545404", R.drawable.salvalozavulcanizing));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Smile Polangco", "Brgy. Punta Pob, Pilar ", "7:00am-6:00pm", "Polangco Vulcanizing Shop", "\n•Change tire/interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "09506087085    ", R.drawable.polangco));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Jeffrey Patestel", " Caridad, Pilar ", "7:00am-6:00pm", "Patestel Vulcanizing Shop", "\n•Change tire/interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Motor Accessories", "09506087085  ", R.drawable.patestel));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Leo Gonzales", " Maasin, Pilar", "7:00am-6:00pm", "Gonzalez Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Change tyre/interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "091005695334   ", R.drawable.gonzalez));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddataSANISIDRO() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Rafael Sampu", " San Isidro", "7:00am-6:00pm", "Sampu Vulcanizing Shop", "\n•Tubeless Repair\n" +
                "•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "09086801757 ", R.drawable.sampu_));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Reneboy Cariaga", "Roxas, San Isidro", "7:00am-6:00pm", "Cariaga Vulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "09086801757    ", R.drawable.cariaga));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Angel Cariaga", "Roxas, San Isidro", "7:00am-6:00pm", "Cariaga Vulcanizing Shop", "\n•Motor parts and Accessories\n" +
                "•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "09086801757 ", R.drawable.cariaga_vulcanizing));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Eriga", "Roxas, San Isidro", "7:00am-6:00pm", "ErigaVulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Pressure\n" +
                "•Vulcanize", "09086801757", R.drawable.erigavulcanizing));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddataSantamonica() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Rafael S. Cacayan ", "Purok 1, Rizal , Sta. Monica", "7:00am-6:00pm", "Cacayan Vulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Air Pressure\n" +
                "•Ring Alignment\n" +
                "•Tubeless Repair\n" +
                "•Motor wiring\n" +
                "•Battery Charging\n" +
                "•Change tyre\n" +
                "•OverWholing", "09505827608  ", R.drawable.cacayan));

        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Julieto Dada", "Magsaysay , Sta. Monica", "7:00am-6:00pm", "Dada Vulcanizing Shop", "\n•Change tire/Interior\n" +
                "•Tire Air Pressure\n" +
                "•Change tyre", "09505827608     ", R.drawable.dada));


        allShopsListAdaptar.notifyDataSetChanged();

    }

    void adddatagenral() {


        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Mark Salvaloza", "Consuelo, General Luna", "7:00am-6:00pm", "Mark Vulcanizing Shop", "\n•Interior/Tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Battery Charging\n" +
                "•Change oil & filter", "no Phone no", R.drawable.mark));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Regie", "Catangnan,General Luna ", "7:00am-6:00pm", "Regie Vulcanizing shop ", "\n•Motor parts\n" +
                "•Accessories\n" +
                "•Change Tire/ Interior\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize", "0950739120 ", R.drawable.regie));
        placeDetailsArrayList.add(new PlaceDetails("Cash Only", "Renbert  Rebuyon", "Purok 1 Poblacion, General Luna ", "7:00am-6:00pm", "Dubai Iron Vulcanizing Shop", "\n•Change Interior/Tire\n" +
                "•Tire Air Pressure\n" +
                "•Vulcanize\n" +
                "•Battery Charging\n" +
                "•Change oil & filter", "09073244043 ", R.drawable.dubai));


        allShopsListAdaptar.notifyDataSetChanged();

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
        startActivity(new Intent(ListOfAllShops.this, ShopsMuncipilityList.class));
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
