package io.vehiclehistory.data.api;

import io.vehiclehistory.api.consts.Settings;
import io.vehiclehistory.api.model.VehicleResponse;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface VehicleHistoryApiInterface {

    public static final String VEHICLE_HISTORY_RESOURCE = "/api/vehicle-history";

    @GET(VEHICLE_HISTORY_RESOURCE)
    @Headers({
            "Accept: " + Settings.API_VERSION_1
    })
    Observable<VehicleResponse> getVehicleHistory(@Query("plate") String plate,
                                                  @Query("vin") String vin,
                                                  @Query("firstRegistrationDate") String firstRegistrationDate);
}
