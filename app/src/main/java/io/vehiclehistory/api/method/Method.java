package io.vehiclehistory.api.method;

import android.content.Context;

import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.api.SslOkHttpClientProvider;
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
    protected Context context;

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Authorization", prepareAuthorization());
            request.addHeader("User-Agent", "VehicleHistory;Android;"+ BuildConfig.VERSION_NAME);
        }
    };

    protected Method(ResponseListener<T> listener, Context context) {
        this.listener = listener;
        this.context = context;
        initializeAdapter();
    }

    protected void initializeAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new SslOkHttpClientProvider().getUnsafeOkHttpClient(context)))
                .setEndpoint(getEndpoint())
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        apiService = restAdapter.create(VehicleHistoryApiInterface.class);
    }

    protected abstract String getEndpoint();

    protected abstract String prepareAuthorization();

    public abstract void makeRequest();
}
