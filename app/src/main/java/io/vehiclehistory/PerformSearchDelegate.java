package io.vehiclehistory;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.api.exception.VehicleHistoryApiException;
import io.vehiclehistory.api.method.AsyncMethodExecutor;
import io.vehiclehistory.api.method.GetVehicleMethod;
import io.vehiclehistory.api.method.ResponseListener;
import io.vehiclehistory.api.method.SessionHandler;
import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;

public class PerformSearchDelegate {

    private static final String TAG = PerformSearchDelegate.class.getSimpleName();

    private static final Integer UNAUTHORIZED = 401;

    private final Activity activity;

    private final AsyncMethodExecutor methodExecutor = new AsyncMethodExecutor();
    private SessionHandler sessionHandler;

    public PerformSearchDelegate(Activity activity) {
        this.activity = activity;
        sessionHandler = new SessionHandler(activity);
    }
    public void performSearch(Search search, OnSearchFinishedListener listener) {
        performSearch(toInput(search), listener);
    }

    public void performSearch(final VehicleInput input, final OnSearchFinishedListener listener) {
        GetVehicleMethod method = new GetVehicleMethod(input, sessionHandler.getSession().getAccessToken(),
                new ResponseListener<VehicleResponse>() {

                    @Override
                    public void onSuccess(VehicleResponse response) {
                        listener.onSearchFinished(response);
                        Intent i = new Intent(activity, VehicleDataActivity.class);
                        i.putExtra(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, response);

                        activity.startActivity(i);
                    }

                    @Override
                    public void onError(VehicleHistoryApiException exception) {
                        if (UNAUTHORIZED.equals(exception.getStatusCode())) {
                            authorizeAndRetry(input, listener);
                        } else {
                            listener.onSearchError(exception.getUserMessage());
                        }
                    }
                }, activity.getApplicationContext());

        methodExecutor.execute(method);
    }

    private void authorizeAndRetry(final VehicleInput input, final OnSearchFinishedListener listener) {
        sessionHandler.getNewSession(new ResponseListener<Auth>() {

            @Override
            public void onSuccess(Auth response) {
                if (!sessionHandler.getSession().getAccessToken().isEmpty()) {
                    performSearch(input, listener);
                } else {
                    Log.e(TAG, "Error fetching token");
                    listener.onSearchError("Can't get token.");
                }
            }

            @Override
            public void onError(VehicleHistoryApiException exception) {
                Log.e(TAG, exception.getMessage());
                listener.onSearchError("Can't get vehicle.");
            }
        });
    }

    private VehicleInput toInput(Search search) {
        VehicleInput input = new VehicleInput();

        input.setPlate(search.getPlate());
        input.setVin(search.getVin());
        input.setFirstRegistrationDate(search.getRegistrationDate());

        return input;
    }

    public interface OnSearchFinishedListener {
        void onSearchFinished(VehicleResponse vehicleResponse);
        void onSearchError(String message);
    }
}
