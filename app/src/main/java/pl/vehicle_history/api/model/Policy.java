package pl.vehicle_history.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public class Policy implements Serializable {
    private PolicyStatus status;

    public PolicyStatus getStatus() {
        return status;
    }
}
