package io.vehiclehistory.validation;

public class Issue {

    public enum Field {
        PLATE, VIN, FIRST_REGISTRATION_DATE
    }

    private final String detailMessage;
    private final Field field;

    public Issue(String detailMessage, Field field) {
        this.detailMessage = detailMessage;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
