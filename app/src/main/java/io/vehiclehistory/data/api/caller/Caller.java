package io.vehiclehistory.data.api.caller;

import io.vehiclehistory.data.api.view.MvpView;
import rx.Subscription;

/**
 * Every presenter in the app must either implement this interface or extend BaseCaller
 * indicating the MvpView type that wants to be attached with.
 */
public interface Caller<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    Subscription call();
}
