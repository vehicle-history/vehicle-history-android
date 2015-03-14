package pl.vehicle_history.api.model;

import org.joda.time.DateTime;

/**
 * TODO: Add a class header comment!
 */
public class VehicleInput {
    private String plate;
    private String vin;
    private DateTime firstRegistrationDate;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public DateTime getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(DateTime firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }
}
