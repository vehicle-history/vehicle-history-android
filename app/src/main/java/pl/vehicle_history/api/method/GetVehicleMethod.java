package pl.vehicle_history.api.method;

import android.util.Log;

import pl.vehicle_history.api.UnsafeOkHttpClientProvider;
import pl.vehicle_history.api.consts.Settings;
import pl.vehicle_history.api.model.VehicleInput;
import pl.vehicle_history.api.model.VehicleResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * TODO: Add a class header comment!
 */
public class GetVehicleMethod extends Method<VehicleResponse> {
    private VehicleInput input;
    private String token;

    public GetVehicleMethod(VehicleInput input, String token, ResponseListener<VehicleResponse> listener) {
        super(listener);
        this.input = input;
        this.token = token;
    }

    @Override
    protected void initializeAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new UnsafeOkHttpClientProvider().getUnsafeOkHttpClient()))
                .setEndpoint(Settings.API_HOST)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(VehicleHistoryApiInterface.class);
    }

    @Override
    public void makeRequest() {
        apiService.getVehicle(input, new Callback<VehicleResponse>() {
            @Override
            public void success(VehicleResponse vehicleResponse, Response response) {
                listener.onSuccess(vehicleResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onError(new Exception());
                Log.d("DEBUG", error.toString());
                //TODO: exception impl
            }
        });
    }

    @Override
    protected String prepareAuthorization() {
        return token;
    }
}
