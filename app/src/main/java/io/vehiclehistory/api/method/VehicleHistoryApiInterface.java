package io.vehiclehistory.api.method;

import io.vehiclehistory.api.consts.MethodRoot;
import io.vehiclehistory.api.consts.Settings;
import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.api.model.VehicleResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface VehicleHistoryApiInterface {
    @Headers({
        "Accept: " + Settings.API_VERSION_1,
        "Language: pl",
    })
    @FormUrlEncoded
    @POST(MethodRoot.OAUTH)
    void getToken(@Field("username") String username,
                  @Field("password") String password,
                  @Field("client_id") String clientId,
                  @Field("grant_type") String grantType,
                  Callback<Auth> onResponse);

    @Headers({
            "Accept: " + Settings.API_VERSION_1,
            "Language: pl",
            "Content-Type: application/x-www-form-urlencoded",
    })
    @GET(MethodRoot.HISTORY)
    void getVehicle(@Query("plate") String plate,
                    @Query("vin") String vin,
                    @Query("firstRegistrationDate") String firstRegistrationDate,
                    Callback<VehicleResponse> onResponse);
}
