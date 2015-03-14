package pl.vehicle_history.api.exception;

/**
 * Created by Dawid on 2015-03-14.
 */
public class VehicleHistoryApiException {
    private String code;
    private String message;
    private Integer statusCode;
    private String userMessage;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
