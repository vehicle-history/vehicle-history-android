package pl.vehicle_history.api.method;

import android.util.Log;

import pl.vehicle_history.api.model.Auth;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dudvar on 2015-03-13.
 */
public class AuthMethod extends Method<Auth> {
    public AuthMethod(ResponseListener<Auth> listener) {
        super(listener);
    }

    @Override
    public void makeRequest() {
        apiService.getToken(getCredentials(), new Callback<Auth>() {
            @Override
            public void success(Auth auth, Response response) {
                listener.onSuccess(auth);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onError(new Exception());
                Log.d("DEBUG", error.toString());
                //TODO: exception impl
            }
        });
    }

    private String getCredentials() {
        return "";
    }
}
