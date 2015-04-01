package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public class Inspection implements Serializable {
    private InspectionStatus status;

    public InspectionStatus getStatus() {
        return status;
    }
}
