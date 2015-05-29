package io.vehiclehistory.api.model;

import java.io.Serializable;

import io.vehiclehistory.R;

/**
 * Created by dudvar on 2015-03-13.
 */
public enum FuelType implements Serializable {
    OTHER(R.string.fuel_type_other),
    PETROL_GAS(R.string.fuel_type_petrol_gas),
    PETROL(R.string.fuel_type_petrol),
    DIESEL(R.string.fuel_type_diesel),
    MIXED_FUEL_OIL(R.string.fuel_type_other),
    ETANOL(R.string.fuel_type_etanol),
    HYBRID(R.string.fuel_type_hybrid),
    ELECTRIC(R.string.fuel_type_electric),
    HYDROGEN(R.string.fuel_type_hydrogen);

    private int valueResource;

    FuelType(int valueResource) {
        this.valueResource = valueResource;
    }

    public int getValueResource() {
        return valueResource;
    }
}
