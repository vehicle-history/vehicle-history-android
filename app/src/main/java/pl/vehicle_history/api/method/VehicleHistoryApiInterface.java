package pl.vehicle_history.api.method;

import pl.vehicle_history.api.consts.MethodRoot;
import pl.vehicle_history.api.consts.Settings;
import pl.vehicle_history.api.model.Auth;
import pl.vehicle_history.api.model.VehicleInput;
import pl.vehicle_history.api.model.VehicleResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface VehicleHistoryApiInterface {
    @Headers({
        "Accept: " + Settings.API_VERSION_1,
        "User-Agent: VehicleHistory;Android",
        "Language: pl",
    })
    @FormUrlEncoded
    @POST(MethodRoot.OAUTH)
    void getToken(@Field("username") String username, @Field("password") String password, @Field("client_id") String clientId, @Field("grant_type") String grantType, Callback<Auth> onResponse);

    @Headers({
            "Accept: " + Settings.API_VERSION_1,
            "User-Agent: VehicleHistory;Android",
            "Language: pl",
            "Content-Type: application/x-www-form-urlencoded",
    })
    @GET(MethodRoot.HISTORY)
    void getVehicle(VehicleInput input, Callback<VehicleResponse> onResponse);
}
