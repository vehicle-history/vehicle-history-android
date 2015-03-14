package pl.vehicle_history.api.method;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import pl.vehicle_history.api.UnsafeOkHttpClientProvider;
import pl.vehicle_history.api.consts.Credentials;
import pl.vehicle_history.api.consts.Settings;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by dudvar on 2015-03-13.
 */
public abstract class Method<T> {
    protected final ResponseListener<T> listener;
    protected RestAdapter restAdapter;
    protected VehicleHistoryApiInterface apiService;

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Authorization", prepareAuthorization());
        }
    };

    protected Method(ResponseListener<T> listener) {
        this.listener = listener;
        initializeAdapter();
    }

    protected void initializeAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new UnsafeOkHttpClientProvider().getUnsafeOkHttpClient()))
                .setEndpoint(Settings.OAUTH_HOST)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(VehicleHistoryApiInterface.class);
    }

    protected abstract String prepareAuthorization();

    public abstract void makeRequest();
}
