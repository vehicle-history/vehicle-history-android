package io.vehiclehistory.validation;

public interface Condition {

    boolean isMet(String value);

    int getErrorMsgResId();

}
