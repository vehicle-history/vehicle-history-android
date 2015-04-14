package io.vehiclehistory.api.model;

import java.io.Serializable;

import io.vehiclehistory.R;

public enum RegistrationStatus implements Serializable {
    UNKNOWN(R.string.registration_status_unknown),
    REGISTERED(R.string.registration_status_registered),
    UNREGISTERED(R.string.registration_status_unregistered);

    private int valueResource;

    RegistrationStatus(int valueResource) {
        this.valueResource = valueResource;
    }

    public int getValueResource() {
        return valueResource;
    }
}
