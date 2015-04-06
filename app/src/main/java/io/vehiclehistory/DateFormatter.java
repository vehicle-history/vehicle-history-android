package io.vehiclehistory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by m4lysh on 2015-04-04.
 */
public class DateFormatter {

    public String formatDateFromApi(String dateFromApi) {
        DateTime dateTime = new DateTime(dateFromApi);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        return formatter.print(dateTime);
    }

}
