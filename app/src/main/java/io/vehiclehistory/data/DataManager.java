package io.vehiclehistory.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.data.api.VehicleHistoryApiInterface;
import io.vehiclehistory.data.api.auth.OnAuthCallback;
import rx.Observable;

@Singleton
public class DataManager {

    private final VehicleHistoryApiInterface vehicleHistoryApiInterface;
    private final AuthProvider authProvider;

    @Inject
    public DataManager(
            VehicleHistoryApiInterface vehicleHistoryApiInterface,
            AuthProvider authProvider
    ) {
        this.vehicleHistoryApiInterface = vehicleHistoryApiInterface;
        this.authProvider = authProvider;
    }

    public Observable<VehicleResponse> getVehicleHistory(VehicleInput request) {
        return vehicleHistoryApiInterface.getVehicleHistory(
                request.getPlate(),
                request.getVin(),
                request.getFirstRegistrationDate()
        );
    }

    public void getNewSession(OnAuthCallback onFinishedListener) {
        authProvider.getNewSession(onFinishedListener);
    }

}
