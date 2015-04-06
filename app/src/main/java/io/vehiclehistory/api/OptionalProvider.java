package io.vehiclehistory.api;

import android.content.Context;
import android.content.res.Resources;

import io.vehiclehistory.DateFormatter;
import io.vehiclehistory.R;
import io.vehiclehistory.api.model.Registration;
import io.vehiclehistory.api.model.Vehicle;

/**
 * Created by m4lysh on 2015-04-06.
 */
public class OptionalProvider {

    private Resources resources;

    public OptionalProvider(Context context) {
        this.resources = context.getResources();
    }

    public String getFirstVehicleRegistration(Vehicle vehicle) {
        Registration registration = vehicle.getRegistration();
        if (registration == null) {
            return resources.getString(R.string.unknown_first_vehicle_registration);
        }

        String firstRegistration = registration.getFirstAt();

        return firstRegistration != null
                ? new DateFormatter().formatDateFromApi(firstRegistration)
                : resources.getString(R.string.unknown_first_vehicle_registration);
    }

}
