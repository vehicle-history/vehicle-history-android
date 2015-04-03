package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public class Registration implements Serializable {
    private RegistrationStatus status;
    private String firstAt;

    public RegistrationStatus getStatus() {
        return status;
    }

    public String getFirstAt() {
        return firstAt;
    }
}
