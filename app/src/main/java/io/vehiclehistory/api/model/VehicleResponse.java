package io.vehiclehistory.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dudvar on 2015-03-13.
 */
public class VehicleResponse implements Serializable {
    private Vehicle vehicle;
    private List<Event> events;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Event> getEvents() {
        return events;
    }
}
