package pl.vehicle_history;

import android.content.Context;

import pl.vehicle_history.Search;
import pl.vehicle_history.api.model.VehicleInput;
import pl.vehicle_history.api.model.VehicleResponse;
import pl.vehicle_history.database.SearchHistoryDb;

public class SaveSearchDelegate {

    SearchHistoryDb searchHistoryDb;

    public SaveSearchDelegate(Context context) {
        searchHistoryDb = new SearchHistoryDb(context);
    }

    public void saveSearch(VehicleInput input, VehicleResponse response) {
        searchHistoryDb.saveSearch(
                new Search(buildLabel(response), input.getPlate(), input.getVin(), input.getFirstRegistrationDate()));
    }

    private String buildLabel(VehicleResponse response) {
        return response.getVehicle().getName().getMake().toString() + " " + response.getVehicle().getName().getModel();
    }
}
