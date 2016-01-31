package io.vehiclehistory.data.api.view;

import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.data.api.auth.OnAuthCallback;

public interface AuthMvpView {

    void onAuth(Auth auth);

    void onRefreshFailed(OnAuthCallback onFinishedListener);

    void onPasswordFailed();

    void onNoConnectionError();
}
