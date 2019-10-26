package usama.utech.nearbyplaceswithdetailsandphotos.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPI {

    @GET
    Call<String> getPath(@Url String url);
}
