package io.vehiclehistory;

/**
 * Created by m4lysh on 2015-03-21.
 */
public class Search {

    private String label;
    private String plate;
    private String vin;
    private String registrationDate;

    public Search(String label, String plate, String vin, String registrationDate) {
        this.label = label;
        this.plate = plate;
        this.vin = vin;
        this.registrationDate = registrationDate;
    }

    public String getLabel() {
        return label;
    }

    public String getPlate() {
        return plate;
    }

    public String getVin() {
        return vin;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}
