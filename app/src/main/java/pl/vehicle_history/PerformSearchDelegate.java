package pl.vehicle_history;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import pl.vehicle_history.activity.VehicleDataActivity;
import pl.vehicle_history.api.exception.VehicleHistoryApiException;
import pl.vehicle_history.api.method.AsyncMethodExecutor;
import pl.vehicle_history.api.method.GetVehicleMethod;
import pl.vehicle_history.api.method.ResponseListener;
import pl.vehicle_history.api.method.SessionHandler;
import pl.vehicle_history.api.model.Auth;
import pl.vehicle_history.api.model.VehicleInput;
import pl.vehicle_history.api.model.VehicleResponse;

public class PerformSearchDelegate {

    private static final String TAG = PerformSearchDelegate.class.getSimpleName();

    private static final int UNAUTHORIZED = 401;

    private final Activity activity;

    private final SessionHandler sessionHandler = new SessionHandler();
    private final AsyncMethodExecutor methodExecutor = new AsyncMethodExecutor();

    public PerformSearchDelegate(Activity activity) {
        this.activity = activity;
    }
    public void performSearch(Search search, OnSearchFinishedListener listener) {
        performSearch(toInput(search), listener);
    }

    public void performSearch(final VehicleInput input, final OnSearchFinishedListener listener) {
        GetVehicleMethod method = new GetVehicleMethod(input, sessionHandler.getSession().getAccessToken(),
                new ResponseListener<VehicleResponse>() {

                    @Override
                    public void onSuccess(VehicleResponse response) {
                        listener.onSearchFinished();
                        Intent i = new Intent(activity, VehicleDataActivity.class);
                        i.putExtra(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, response);
                        activity.startActivity(i);
                    }

                    @Override
                    public void onError(VehicleHistoryApiException exception) {
                        if (exception != null && exception.getStatusCode() == UNAUTHORIZED) {
                            authorizeAndRetry(input, listener);
                        } else {
                            Log.e(TAG, "Network error");
                            listener.onSearchError(exception.getUserMessage());
                        }
                    }
                });

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
        void onSearchFinished();
        void onSearchError(String message);
    }
}
