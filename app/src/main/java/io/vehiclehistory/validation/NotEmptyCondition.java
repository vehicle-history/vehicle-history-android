package io.vehiclehistory.validation;

import io.vehiclehistory.R;

public class NotEmptyCondition implements Condition {

    @Override
    public boolean isMet(String value) {
        return !value.isEmpty();
    }

    @Override
    public int getErrorMsgResId() {
        return R.string.condition_msg_not_empty;
    }
}
