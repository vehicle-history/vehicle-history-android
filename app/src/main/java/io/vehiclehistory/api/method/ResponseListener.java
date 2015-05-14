package io.vehiclehistory.api.method;

import io.vehiclehistory.api.exception.VehicleHistoryApiException;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface ResponseListener<T> {
    void onSuccess(T response);
    void onApiError(VehicleHistoryApiException exception);
    void onConnectionError(String message);
}
