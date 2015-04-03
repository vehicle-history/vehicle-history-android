package io.vehiclehistory;

import android.content.Context;

import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.database.SearchHistoryDb;

public class SaveSearchDelegate {

    SearchHistoryDb searchHistoryDb;

    public SaveSearchDelegate(Context context) {
        searchHistoryDb = new SearchHistoryDb(context);
    }

    public void saveSearch(VehicleInput input, VehicleResponse response) {
        searchHistoryDb.saveOrUpdateSearch(
                new Search(buildLabel(response), input.getPlate(), input.getVin(), input.getFirstRegistrationDate()));
    }

    private String buildLabel(VehicleResponse response) {
        return response.getVehicle().getName().getMake().toString() + " " + response.getVehicle().getName().getModel();
    }
}
