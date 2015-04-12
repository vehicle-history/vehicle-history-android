package io.vehiclehistory.api.model;

import org.apache.commons.lang3.text.WordUtils;

import java.io.Serializable;

/**
 * Created by dudvar on 2015-03-13.
 */
public enum CarMake implements Serializable {
    UNKNOWN,
    AUDI,
    ALFA_ROMEO,
    BENTLEY,
    BRENDERUP,
    BMW,
    CHEVROLET,
    CHRYSLER,
    CITROEN,
    DACIA,
    DAF,
    DAEWOO,
    DODGE,
    DAIMLER,
    FIAT,
    FERRO,
    FSO,
    FSM,
    FORD,
    GMC,
    HONDA,
    HYUNDAI,
    HYOSUNG,
    JAGUAR,
    JEEP,
    YAMAHA,
    KIA,
    KAWASAKI,
    LANCIA,
    LIAZ,
    LEXUS,
    LAND_ROVER,
    MERCEDES,
    MAZDA,
    MINI,
    MITSUBISHI,
    MAJESTIC_AUTO,
    NISSAN,
    MZ,
    MAN,
    OPEL,
    PEUGEOT,
    RENAULT,
    ROLLS_ROYCE,
    RIEJU,
    ROVER,
    ROMET_MOTORS,
    SAM,
    SIMSON,
    SETRA,
    SCANIA,
    SPRITE_SPORT,
    SAAB,
    SEAT,
    SKODA,
    SUZUKI,
    SMART,
    TOYOTA,
    TRIUMPH,
    TEMA,
    URSUS,
    VOLKSWAGEN,
    VOLVO,
    VAUXHALL,
    WANGYE,
    WAZ,
    YAMASAKI;

    public String getUserLabel() {
        return WordUtils.capitalizeFully(this.toString().replaceAll("_", " "));
    }
}
