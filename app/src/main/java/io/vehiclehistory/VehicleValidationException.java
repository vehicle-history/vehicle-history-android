package io.vehiclehistory;

public class VehicleValidationException extends Exception {

    public enum Field {
        PLATE, VIN, FIRST_REGISTRATION_DATE
    }

    private final Field field;

    public VehicleValidationException(String detailMessage, Field field) {
        super(detailMessage);
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
