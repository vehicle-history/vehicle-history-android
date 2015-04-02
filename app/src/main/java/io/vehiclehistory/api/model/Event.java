package io.vehiclehistory.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Event implements Serializable {
    private EventType type;
    private String createdAt;
    private String expireAt;
    @SerializedName("firstOwner")
    private Boolean isFirstOwner;
    private String note;
    private OwnerType ownerType;
    private Location location;
    @SerializedName("abroadRegistration")
    private Boolean isAbroadRegistration;
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

    public Boolean getIsFirstOwner() {
        return isFirstOwner;
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

    public Boolean getIsAbroadRegistration() {
        return isAbroadRegistration;
    }

    public Mileage getMileage() {
        return mileage;
    }
}
