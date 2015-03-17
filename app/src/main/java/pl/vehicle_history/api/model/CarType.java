package pl.vehicle_history.api.model;

import java.io.Serializable;

/**
 * TODO: Add a class header comment!
 */
public enum CarType implements Serializable {
    UNKNOWN,
    CAR,
    SPECIAL_CAR,
    OTHER_CAR,
    MOTORCYCLE,
    MOPED,
    BUS,
    TRACTOR,
    LIGHT_TRAILER,
    TRUCK
}
