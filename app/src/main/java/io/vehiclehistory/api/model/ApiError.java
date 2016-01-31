package io.vehiclehistory.api.model;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    private String code;
    private String message;
    private Integer statusCode;
    private String userMessage;

    //from oauth
    private String error;

    //from oauth
    @SerializedName("error_description")
    private String errorDescription;


    public String getCode() {
        return code;
    }

    public String getMessage() {
        if (message != null) {
            return message;
        }

        return errorDescription;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static ApiError internalApiError() {
        ApiError apiError = new ApiError();
        apiError.setStatusCode(500);
        apiError.message = "internalApiError";
        apiError.code = "internalApiError";
        return apiError;
    }
}
