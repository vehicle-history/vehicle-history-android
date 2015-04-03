package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public class Plate implements Serializable {
    private String value;
    private Country country;

    public String getValue() {
        return value;
    }

    public Country getCountry() {
        return country;
    }
}
