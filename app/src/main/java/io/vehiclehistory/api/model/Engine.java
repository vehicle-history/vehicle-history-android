package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Engine implements Serializable {
    private String cubicCapacity;
    private FuelType fuel;

    public String getCubicCapacity() {
        return cubicCapacity;
    }

    public FuelType getFuel() {
        return fuel;
    }
}
