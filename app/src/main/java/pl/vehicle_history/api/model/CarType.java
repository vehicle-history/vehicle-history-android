package pl.vehicle_history.api.model;

import java.io.Serializable;

import pl.vehicle_history.historiapojazdu.R;

/**
 * TODO: Add a class header comment!
 */
public enum CarType implements Serializable {
    UNKNOWN(R.string.car_type_unknown),
    CAR(R.string.car_type_car),
    SPECIAL_CAR(R.string.car_type_special_car),
    OTHER_CAR(R.string.car_type_other_car),
    MOTORCYCLE(R.string.car_type_motorcycle),
    MOPED(R.string.car_type_moped),
    BUS(R.string.car_type_bus),
    TRACTOR(R.string.car_type_tractor),
    LIGHT_TRAILER(R.string.car_type_light_trailer),
    TRUCK(R.string.car_type_light_truck);

    private int valueResource;

    CarType(int valueResource) {
        this.valueResource = valueResource;
    }

    public int getValueResource() {
        return valueResource;
    }
}
