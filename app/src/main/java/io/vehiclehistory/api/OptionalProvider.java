package io.vehiclehistory.api;

import android.content.Context;
import android.content.res.Resources;

import io.vehiclehistory.DateFormatter;
import io.vehiclehistory.R;
import io.vehiclehistory.api.model.CarKind;
import io.vehiclehistory.api.model.CarMake;
import io.vehiclehistory.api.model.CarType;
import io.vehiclehistory.api.model.Engine;
import io.vehiclehistory.api.model.FuelType;
import io.vehiclehistory.api.model.Inspection;
import io.vehiclehistory.api.model.InspectionStatus;
import io.vehiclehistory.api.model.Mileage;
import io.vehiclehistory.api.model.Name;
import io.vehiclehistory.api.model.Plate;
import io.vehiclehistory.api.model.Policy;
import io.vehiclehistory.api.model.PolicyStatus;
import io.vehiclehistory.api.model.Production;
import io.vehiclehistory.api.model.Registration;
import io.vehiclehistory.api.model.RegistrationStatus;
import io.vehiclehistory.api.model.Vehicle;
import io.vehiclehistory.api.model.VehicleType;

/**
 * Created by m4lysh on 2015-04-06.
 */
public class OptionalProvider {

    private Resources resources;

    public OptionalProvider(Context context) {
        this.resources = context.getResources();
    }

    public String getFirstVehicleRegistration(Vehicle vehicle) {
        String date = resources.getString(R.string.unknown_first_vehicle_registration);
        Registration registration = vehicle.getRegistration();

        if (registration != null && registration.getFirstAt() != null) {
            date = new DateFormatter().formatDateFromApi(registration.getFirstAt());
        }

        return date;
    }

    public String getRegistrationStatus(Vehicle vehicle) {
        Registration registration = vehicle.getRegistration();
        if (registration == null) {
            return resources.getString(R.string.unknown_registration_status);
        }

        RegistrationStatus status = registration.getStatus();

        return status != null
                ? resources.getString(status.getValueResource())
                : resources.getString(R.string.unknown_registration_status);
    }

    public String getMakePlusModel(Vehicle vehicle) {
        Name name = vehicle.getName();
        if (name == null) {
            return resources.getString(R.string.unknown_car);
        }

        String model = name.getModel();
        CarMake make = name.getMake();

        if (model == null && make == null) {
            return resources.getString(R.string.unknown_car);
        }

        return buildTitle(model, make);
    }

    private String buildTitle(String model, CarMake make) {
        StringBuilder sb = new StringBuilder();

        if (make != null) {
            sb.append(make.getUserLabel());
            sb.append(" ");
        }

        if (model != null) {
            sb.append(model);
        }

        return sb.toString();
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
                ? make.getUserLabel() : resources.getString(R.string.unknown_make);
    }

    public String getCubicCapacity(Vehicle vehicle) {
        Engine engine = vehicle.getEngine();
        if (engine == null) {
            return resources.getString(R.string.unknown_cubic_capacity);
        }

        String engineCc = engine.getCubicCapacity();

        return engineCc != null
                ? engineCc : resources.getString(R.string.unknown_cubic_capacity);
    }

    public String getFuelType(Vehicle vehicle) {
        Engine engine = vehicle.getEngine();
        if (engine == null) {
            return resources.getString(R.string.unknown_fuel_type);
        }

        FuelType fuelType = engine.getFuel();

        return fuelType != null
                ? resources.getString(fuelType.getValueResource())
                : resources.getString(R.string.unknown_fuel_type);
    }

    public String getMileage(Vehicle vehicle) {
        Mileage mileage = vehicle.getMileage();
        if (mileage == null) {
            return resources.getString(R.string.unknown_mileage);
        }

        String fuelType = mileage.getValue();

        return fuelType != null
                ? fuelType : resources.getString(R.string.unknown_mileage);
    }

    public String hasInsurance(Vehicle vehicle) {
        Policy policy = vehicle.getPolicy();
        if (policy == null) {
            return resources.getString(R.string.unknown_policy_status);
        }

        PolicyStatus policyStatus = policy.getStatus();

        return policyStatus != null
                ? resources.getString(policyStatus.getValueResource())
                : resources.getString(R.string.unknown_policy_status);
    }

    public String getInspectionStatus(Vehicle vehicle) {
        Inspection inspection = vehicle.getInspection();
        if (inspection == null) {
            return resources.getString(R.string.unknown_inspection_status);
        }

        InspectionStatus inspectionStatus = inspection.getStatus();

        return inspectionStatus != null
                ? resources.getString(inspectionStatus.getValueResource())
                : resources.getString(R.string.unknown_inspection_status);
    }

    public String getVehicleType(Vehicle vehicle) {
        VehicleType type = vehicle.getType();
        if (type == null) {
            return resources.getString(R.string.unknown_vehicle_type);
        }

        CarType vehicleType = type.getType();

        return vehicleType != null
                ? resources.getString(vehicleType.getValueResource())
                : resources.getString(R.string.unknown_vehicle_type);
    }

    public String getVehicleKind(Vehicle vehicle) {
        VehicleType type = vehicle.getType();
        if (type == null) {
            return resources.getString(R.string.unknown_vehicle_kind);
        }

        CarKind carKind = type.getKind();

        return carKind != null
                ? resources.getString(carKind.getValueResource())
                : resources.getString(R.string.unknown_vehicle_kind);
    }

    public String getProductionYear(Vehicle vehicle) {
        Production production = vehicle.getProduction();
        if (production == null) {
            return resources.getString(R.string.unknown_production_year);
        }

        String year = production.getYear();

        return year != null
                ? year : resources.getString(R.string.unknown_production_year);
    }

    public String getPlate(Vehicle vehicle) {
        Plate plate = vehicle.getPlate();
        if (plate == null) {
            return resources.getString(R.string.unknown_plate);
        }

        String plateValue = plate.getValue();

        return plateValue != null ? plateValue : resources.getString(R.string.unknown_plate);
    }

    public String getVin(Vehicle vehicle) {
        String vin = vehicle.getVin();

        return vin != null ? vin : resources.getString(R.string.unknown_vin);
    }

    public String getStolen(Vehicle vehicle) {
        Boolean stolen = vehicle.getStolen();

        if (stolen == null) {
            return resources.getString(R.string.no_data);
        }

        return stolen ? resources.getString(R.string.yes) : resources.getString(R.string.no);
    }
}
