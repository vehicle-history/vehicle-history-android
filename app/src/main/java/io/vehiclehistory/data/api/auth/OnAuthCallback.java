package io.vehiclehistory.data.api.auth;

import io.vehiclehistory.api.model.Auth;

public interface OnAuthCallback {
    void onSuccess(Auth response);

    void onError();

}
