package io.vehiclehistory;

import io.vehiclehistory.api.model.EventType;

public class EventColorProvider {

    public int getBackground(EventType type) {
        switch (type) {
            case PRODUCTION:
                return R.drawable.circle_amber;
            case REGISTRATION:
            case ABROAD_REGISTRATION:
            case DEREGISTRATION:
            case CHANGED_REGISTRATION_LOCATION:
            case HOLDER:
                return R.drawable.circle_purple;
            case CHANGE_OWNER:
            case CO_OWNER:
                return R.drawable.circle_blue;
            case INSPECTION:
                return R.drawable.circle_green;
            case STOLEN:
                return R.drawable.circle_pink;
            case UNKNOWN:
            default:
                return R.drawable.circle_amber;
        }
    }
}
