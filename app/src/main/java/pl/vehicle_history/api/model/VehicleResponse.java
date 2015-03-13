package pl.vehicle_history.api.model;

import java.util.List;

/**
 * Created by dawid.mackowiak on 2015-03-13.
 */
public class VehicleResponse {
    private Vehicle vehicle;
    private List<Event> events;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Event> getEvents() {
        return events;
    }
}
