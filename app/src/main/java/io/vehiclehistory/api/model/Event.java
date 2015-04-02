package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Event implements Serializable {
    private EventType type;
    private String createdAt;
    private String expireAt;
    private Boolean firstOwner;
    private String note;
    private OwnerType ownerType;
    private Location location;
    private Boolean abroadRegistration;
    private Mileage mileage;

    public EventType getType() {
        return type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public Boolean getFirstOwner() {
        return firstOwner;
    }

    public String getNote() {
        return note;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public Location getLocation() {
        return location;
    }

    public Boolean getAbroadRegistration() {
        return abroadRegistration;
    }

    public Mileage getMileage() {
        return mileage;
    }
}
