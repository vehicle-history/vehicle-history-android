package pl.vehicle_history.api.model;

/**
 * Created by dawid.mackowiak on 2015-03-13.
 */
public class Engine {
    private String cubicCapacity;
    private FuelType fuel;

    public String getCubicCapacity() {
        return cubicCapacity;
    }

    public FuelType getFuel() {
        return fuel;
    }
}
