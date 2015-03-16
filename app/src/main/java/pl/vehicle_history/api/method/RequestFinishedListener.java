package pl.vehicle_history.api.method;

/**
 * Created by Dawid on 2015-03-16.
 */
public interface RequestFinishedListener {
    void onFinished();
    void onException();
}
