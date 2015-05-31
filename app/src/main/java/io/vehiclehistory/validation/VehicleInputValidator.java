package io.vehiclehistory.validation;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collection;

import io.vehiclehistory.validation.Issue.Field;

public class VehicleInputValidator {

    private final Resources resources;

    private final Collection<Issue> issues = new ArrayList<>();

    public VehicleInputValidator(Context context) {
        this.resources = context.getResources();
    }

    public void validate(String plate, String vin, String firstRegDate) throws VehicleValidationException {
        issues.clear();

        check(Field.PLATE, plate, new NotEmptyCondition());
        check(Field.VIN, vin, new NotEmptyCondition());
        check(Field.FIRST_REGISTRATION_DATE, firstRegDate, new NotEmptyCondition());

        if (!issues.isEmpty()) {
            throw new VehicleValidationException(issues);
        }
    }

    private void check(Field field, String value, Condition condition) {
        if (!condition.isMet(value)) {
            issues.add(new Issue(resources.getString(condition.getErrorMsgResId()), field));
        }
    }

}
