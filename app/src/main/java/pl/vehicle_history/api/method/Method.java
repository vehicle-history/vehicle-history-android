package pl.vehicle_history.api.method;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import pl.vehicle_history.api.consts.Credentials;
import pl.vehicle_history.api.consts.Settings;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

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
                .setEndpoint(Settings.OAUTH_HOST)
                .setRequestInterceptor(requestInterceptor)
                .build();
        apiService = restAdapter.create(VehicleHistoryApiInterface.class);
    }

    public String prepareAuthorization()     {
        // Prepare authorization data
        String credentialsToken = Credentials.CLIENT + ":" + Credentials.CLIENT_PASSWORD;

        byte[] bytes = new byte[0];
        try {
            bytes = credentialsToken.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        String authToken = "Basic" + " " + base64;
        return authToken;
    }

    public abstract void makeRequest();
}
