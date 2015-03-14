package pl.vehicle_history.api.model;

import org.joda.time.DateTime;

/**
 * TODO: Add a class header comment!
 */
public class Registration {
    private RegistrationStatus status;
    private DateTime firstAt;

    public RegistrationStatus getStatus() {
        return status;
    }

    public DateTime getFirstAt() {
        return firstAt;
    }
}
