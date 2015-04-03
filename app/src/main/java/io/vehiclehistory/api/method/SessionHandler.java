package io.vehiclehistory.api.method;

import io.vehiclehistory.api.exception.VehicleHistoryApiException;
import io.vehiclehistory.api.model.Auth;

/**
 * Created by Dawid on 2015-03-16.
 */
public class SessionHandler {
    private final AsyncMethodExecutor methodExecutor = new AsyncMethodExecutor();
    private Auth session = new Auth();

    public Auth getSession() {
        return session;
    }

    public void getNewSession(final ResponseListener onFinishedListener) {
        AuthMethod authMethod = new AuthMethod(new ResponseListener<Auth>() {
            @Override
            public void onSuccess(Auth response) {
                session = response;
                onFinishedListener.onSuccess(response);
            }

            @Override
            public void onError(VehicleHistoryApiException exception) {
                onFinishedListener.onError(exception);
            }
        });
        methodExecutor.execute(authMethod);
    }
}
