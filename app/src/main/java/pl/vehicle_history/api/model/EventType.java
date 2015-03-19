package pl.vehicle_history.api.model;

import pl.vehicle_history.historiapojazdu.R;

/**
 * TODO: Add a class header comment!
 */
public enum EventType {
    UNKNOWN(R.string.unknown),
    PRODUCTION(R.string.timeline_production),
    REGISTRATION(R.string.timeline_registration),
    DEREGISTRATION(R.string.timeline_deregistration),
    CHANGE_OWNER(R.string.timeline_change_owner),
    INSPECTION(R.string.timeline_inspection),
    CO_OWNER(R.string.timeline_co_owner),
    HOLDER(R.string.timeline_holder),
    CHANGED_REGISTRATION_LOCATION(R.string.timeline_changed_registration_location),
    STOLEN(R.string.timeline_stolen);

    private final int valueResource;

    private EventType(final int valueResource) {
        this.valueResource = valueResource;
    }
    public int getValueResource() {
        return valueResource;
    }
}

