package pl.vehicle_history.api.method;

import pl.vehicle_history.api.consts.MethodRoot;
import pl.vehicle_history.api.model.Auth;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface VehicleHistoryApiInterface {
    @POST(MethodRoot.OAUTH)
    void getToken(@Body String credentials, Callback<Auth> onResponse);
}
