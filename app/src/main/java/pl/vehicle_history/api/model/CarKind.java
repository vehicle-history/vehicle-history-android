package pl.vehicle_history.api.model;

import java.io.Serializable;

import pl.vehicle_history.historiapojazdu.R;

/**
 * TODO: Add a class header comment!
 */
public enum CarKind implements Serializable {
    UNKNOWN(R.string.car_kind_unknown),
    OTHER(R.string.car_kind_other),
    HATCHBACK(R.string.car_kind_hatchback),
    LIMOUSINE(R.string.car_kind_limousine),
    SEDAN(R.string.car_kind_sedan),
    CONVERTIBLE(R.string.car_kind_convertible),
    COUPE(R.string.car_kind_coupe),
    ESTATE(R.string.car_kind_estate),
    HEARSE(R.string.car_kind_hearse),
    OFF_ROAD(R.string.car_kind_off_road),
    VAN(R.string.car_kind_van),
    WAGON(R.string.car_kind_wagon),
    STANDARD(R.string.car_kind_standard),
    SPORT(R.string.car_kind_sport),
    CRUISER(R.string.car_kind_cruiser),
    TOURING(R.string.car_kind_touring),
    SPORT_TOURING(R.string.car_kind_sport_touring),
    QUAD(R.string.car_kind_quad),
    WHEELED(R.string.car_kind_wheeled),
    TRAILER(R.string.car_kind_trailer),
    OTHER_NOT_CAMPING(R.string.car_kind_other_not_camping),
    LOAD_CAPACITY_OF_8_TONS(R.string.car_kind_load_capacity_of_8_tons),
    CITY(R.string.car_kind_city);

    private final int valueResource;

    CarKind(final int valueResource) {
        this.valueResource = valueResource;
    }
    public int getValueResource() {
        return valueResource;
    }
}
