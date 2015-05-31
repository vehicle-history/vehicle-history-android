package io.vehiclehistory.validation;

import java.util.Collection;

public class VehicleValidationException extends Exception {

    private final Collection<Issue> issues;

    public VehicleValidationException(Collection<Issue> issues) {
        super();

        this.issues = issues;
    }

    public Collection<Issue> getIssues() {
        return issues;
    }
}
