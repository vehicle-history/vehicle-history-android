package pl.vehicle_history.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public class Mileage implements Serializable {
    private String value;
    private MileageType type;

    public String getValue() {
        return value;
    }

    public MileageType getType() {
        return type;
    }
}
