package io.vehiclehistory.validation;

import java.util.ArrayList;
import java.util.Collection;

public class VehicleValidationException extends Exception {

    private final Collection<Issue> issues = new ArrayList<>();

    public VehicleValidationException() {
        super();
    }

    public VehicleValidationException withIssue(Issue issue) {
        issues.add(issue);
        return this;
    }

    public Collection<Issue> getIssues() {
        return issues;
    }
}
