package io.vehiclehistory.api;

import android.content.Context;
import android.content.res.Resources;

import io.vehiclehistory.DateFormatter;
import io.vehiclehistory.R;
import io.vehiclehistory.api.model.CarMake;
import io.vehiclehistory.api.model.Name;
import io.vehiclehistory.api.model.Registration;
import io.vehiclehistory.api.model.RegistrationStatus;
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

    public String getRegistrationStatus(Vehicle vehicle) {
        Registration registration = vehicle.getRegistration();
        if (registration == null) {
            return resources.getString(R.string.unknown_registration_status);
        }

        RegistrationStatus status = registration.getStatus();

        return status != null
                ? status.toString()
                : resources.getString(R.string.unknown_registration_status);
    }

    public String getModel(Vehicle vehicle) {
        Name name = vehicle.getName();
        if (name == null) {
            return resources.getString(R.string.unknown_model);
        }

        String model = name.getModel();

        return model != null
                ? model : resources.getString(R.string.unknown_model);
    }

    public String getMake(Vehicle vehicle) {
        Name name = vehicle.getName();
        if (name == null) {
            return resources.getString(R.string.unknown_make);
        }

        CarMake make = name.getMake();

        return make != null
                ? make.toString() : resources.getString(R.string.unknown_make);
    }

}
