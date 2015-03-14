package pl.vehicle_history.api.method;

import pl.vehicle_history.api.exception.VehicleHistoryApiException;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface ResponseListener<T> {
    void onSuccess(T response);
    void onError(VehicleHistoryApiException exception);
}
