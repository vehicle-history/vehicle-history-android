package io.vehiclehistory.api.model;

import java.io.Serializable;

import io.vehiclehistory.R;

public enum OwnerType implements Serializable {
    UNKNOWN(0),
    PRIVATE(R.string.owner_type_private),
    COMPANY(R.string.owner_type_company);

    private int valueResource;

    OwnerType(int valueResource) {
        this.valueResource = valueResource;
    }

    public int getValueResource() {
        return valueResource;
    }
}
