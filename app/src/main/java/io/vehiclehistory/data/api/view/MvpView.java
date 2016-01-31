package io.vehiclehistory.data.api.view;


/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Caller)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {
    void onErrorResponse(String message);

    void onNoConnectionError();

    void onRetryError();

    void unableToGetTokenError();

    void startedLoadingData();

    void finishedLoadingData();

}
