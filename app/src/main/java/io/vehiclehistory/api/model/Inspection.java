package io.vehiclehistory.api.model;

import java.io.Serializable;

public class Inspection implements Serializable {
    private InspectionStatus status;

    public InspectionStatus getStatus() {
        return status;
    }
}
