package io.vehiclehistory.api.model;

import java.io.Serializable;

public class Location implements Serializable {
    private String state;
    private Country country;

    public String getState() {
        return state;
    }

    public Country getCountry() {
        return country;
    }
}
