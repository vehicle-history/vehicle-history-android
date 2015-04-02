package io.vehiclehistory.api.method;

import io.vehiclehistory.api.UnsafeOkHttpClientProvider;
import io.vehiclehistory.api.consts.Settings;
import io.vehiclehistory.api.exception.VehicleHistoryApiException;
import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class GetVehicleMethod extends Method<VehicleResponse> {

    private static final String BEARER_PREFIX = "Bearer ";

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
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(VehicleHistoryApiInterface.class);
    }

    @Override
    public void makeRequest() {
        apiService.getVehicle(input.getPlate(), input.getVin(), input.getFirstRegistrationDate(),
                new Callback<VehicleResponse>() {

            @Override
            public void success(VehicleResponse vehicleResponse, Response response) {
                listener.onSuccess(vehicleResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                VehicleHistoryApiException exception =
                        (VehicleHistoryApiException) error.getBodyAs(VehicleHistoryApiException.class);

                listener.onError(exception);
            }
        });
    }

    @Override
    protected String prepareAuthorization() {
        return BEARER_PREFIX + token;
    }
}
