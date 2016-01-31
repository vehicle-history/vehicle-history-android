package io.vehiclehistory.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.api.consts.Credentials;
import io.vehiclehistory.api.consts.Settings;
import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.components.ObscuredSharedPreferences;
import io.vehiclehistory.data.AuthProvider;
import io.vehiclehistory.data.api.VehicleHistoryApiInterface;
import io.vehiclehistory.data.api.auth.AuthApiService;
import io.vehiclehistory.data.api.auth.AuthFromPasswordCaller;
import io.vehiclehistory.data.api.auth.AuthFromRefreshTokenCaller;
import io.vehiclehistory.fragment.FindVehicleFragment;
import io.vehiclehistory.injection.ApplicationContext;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return new ObscuredSharedPreferences(
                application, PreferenceManager.getDefaultSharedPreferences(application)
        );
    }

    @Provides
    @Singleton
    AuthProvider provideSessionHandler(AuthFromPasswordCaller authFromPasswordCaller, AuthFromRefreshTokenCaller authFromRefreshTokenCaller, SharedPreferences sharedPreferences, Auth auth) {
        return new AuthProvider(authFromPasswordCaller, authFromRefreshTokenCaller, sharedPreferences, auth);
    }

    @Provides
    @Singleton
    AuthFromPasswordCaller provideAuthFromPasswordProvider(AuthApiService authApiService) {
        return new AuthFromPasswordCaller(authApiService);
    }

    @Provides
    @Singleton
    AuthFromRefreshTokenCaller provideAuthFromRefreshTokenProvider(AuthApiService authApiService) {
        return new AuthFromRefreshTokenCaller(authApiService);
    }

    @Provides
    @Singleton
    Auth provideAuth() {
        SharedPreferences sharedPreferences = provideSharedPreferences();
        String accessToken = sharedPreferences.getString(AuthProvider.ACCESS_TOKEN, null);
        String refreshToken = sharedPreferences.getString(AuthProvider.REFRESH_TOKEN, null);

        Auth auth = new Auth();
        auth.setAccessToken(accessToken);
        auth.setRefreshToken(refreshToken);

        return auth;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Timber.d("intercept for request: %s", chain.request());

                String authorization = getAuthorization(chain.request());

                Timber.d("set request Authorization header to: \"%s\"", authorization);

                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("Accept-language", "pl")
                        .addHeader("Authorization", authorization)
                        .addHeader("User-Agent", "VehicleHistory;Android;" + BuildConfig.VERSION_NAME)
                        .build();
                return chain.proceed(newRequest);
            }

            @NonNull
            private String getAuthorization(Request request) throws IOException {
                //default

                String accessToken = provideAuth().getAccessToken();
                String authorization = Credentials.BEARER_AUTHORIZATION + " " + accessToken;

                Timber.d("request intercept for path: %s", request.url().encodedPath());

                if (AuthApiService.OAUTH_TOKEN.equals(request.url().encodedPath())) {
                    String credentialsToken = Credentials.CLIENT + ":" + Credentials.CLIENT_PASSWORD;

                    byte[] bytes = new byte[0];

                    try {
                        bytes = credentialsToken.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Timber.e(e, "UnsupportedEncodingException while preparing authorization data");
                    }

                    String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    authorization = Credentials.BASIC_AUTHORIZATION + " " + base64;
                }
                return authorization;
            }
        };

        try {
            InputStream inputStream = provideContext().getAssets().open(Settings.TRUSTSTORE_NAME);

            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(inputStream, null);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keystore);

            X509TrustManager defaultTrustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{defaultTrustManager}, null);

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(Settings.READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .connectTimeout(Settings.CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return Settings.HOSTNAME.equals(hostname);
                        }
                    });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.tag("OkHttp").d(message);
                    }
                });

                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(loggingInterceptor);
            }

            return okHttpClientBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder()
                .create();

        return new Retrofit.Builder()
                .baseUrl(Settings.HOST)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    AuthApiService provideAuthApiService() {
        return provideRetrofit().create(AuthApiService.class);
    }

    @Provides
    @Singleton
    VehicleHistoryApiInterface provideVehicleHistoryApiInterface() {
        return provideRetrofit().create(VehicleHistoryApiInterface.class);
    }
}
