package io.vehiclehistory.data.api.view;

import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;

public interface VehicleHistoryMvpView extends MvpView {

    void onGetVehicleHistoryFinished(VehicleInput request, VehicleResponse response);
}
