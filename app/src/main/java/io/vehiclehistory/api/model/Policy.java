package io.vehiclehistory.api.model;

import java.io.Serializable;

public class Policy implements Serializable {
    private PolicyStatus status;

    public PolicyStatus getStatus() {
        return status;
    }
}
