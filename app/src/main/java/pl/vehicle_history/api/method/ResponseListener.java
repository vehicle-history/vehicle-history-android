package pl.vehicle_history.api.method;

/**
 * Created by dudvar on 2015-03-13.
 */
public interface ResponseListener<T> {
    void onSuccess(T response);
    void onError(Exception exception);
}
