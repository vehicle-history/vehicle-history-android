package io.vehiclehistory.api.consts;

/**
 * Created by dudvar on 2015-03-13.
 */
public class Settings {
    public static final String TRUSTSTORE_NAME = "vehiclehistory.io.truststore.bks";
    public static final String HOSTNAME = "vehicle-history.io";
    public static final String HOST = "https://" + HOSTNAME;
    public static final Integer RETRY_COUNT = 1;
    public static final Integer READ_TIMEOUT_MS = 10000;
    public static final Integer CONNECTION_TIMEOUT_MS = 5000;
    public static final String API_VERSION_1 = "application/vnd.vehicle-history.v1+json";
}
