package io.vehiclehistory.api.model;

import java.io.Serializable;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Name implements Serializable {
    private CarMake make;
    private String carName;
    private String model;

    public CarMake getMake() {
        return make;
    }

    public String getCarName() {
        return carName;
    }

    public String getModel() {
        return model;
    }
}
