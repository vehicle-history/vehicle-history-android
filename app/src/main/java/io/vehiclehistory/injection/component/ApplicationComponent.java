package io.vehiclehistory.injection.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import io.vehiclehistory.VehicleHistoryApp;
import io.vehiclehistory.api.model.Auth;
import io.vehiclehistory.data.AuthProvider;
import io.vehiclehistory.data.DataManager;
import io.vehiclehistory.data.api.auth.AuthApiService;
import io.vehiclehistory.data.api.caller.GetVehicleHistoryCaller;
import io.vehiclehistory.fragment.FindVehicleFragment;
import io.vehiclehistory.fragment.SearchHistoryFragment;
import io.vehiclehistory.injection.ApplicationContext;
import io.vehiclehistory.injection.module.ApplicationModule;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    SharedPreferences sharedPreferences();

    DataManager dataManager();

    AuthProvider sessionHandler();

    Auth auth();

    Retrofit retrofit();

    AuthApiService authApiService();

    void inject(VehicleHistoryApp vehicleHistoryApp);

    void inject(GetVehicleHistoryCaller delegate);

    void inject(AuthProvider authProvider);

    void inject(FindVehicleFragment findVehicleFragment);

    void inject(SearchHistoryFragment searchHistoryFragment);
}
