package usama.utech.nearbyplaceswithdetailsandphotos.Common;

import java.util.List;

import usama.utech.nearbyplaceswithdetailsandphotos.model.Place;
import usama.utech.nearbyplaceswithdetailsandphotos.remote.IGoogleAPI;
import usama.utech.nearbyplaceswithdetailsandphotos.remote.RetrofitClient;


public class Common {


    public static final String baseURL = "https://maps.googleapis.com";

    public static List<Place> placeArrayList_static ;


    public static IGoogleAPI getGoogleAPI() {

        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }


}

