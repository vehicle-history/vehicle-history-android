package io.vehiclehistory;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.vehiclehistory.injection.component.ApplicationComponent;
import io.vehiclehistory.injection.component.DaggerApplicationComponent;
import io.vehiclehistory.injection.module.ApplicationModule;
import timber.log.Timber;

public class VehicleHistoryApp extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        configureFabric();
        configureStrictMode();
        configureLogging();
    }

    private void configureFabric() {
        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void configureLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void configureStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeathOnNetwork().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().detectActivityLeaks().penaltyLog().build());
    }

    public static VehicleHistoryApp get(Context context) {
        return (VehicleHistoryApp) context.getApplicationContext();
    }

    public ApplicationComponent component() {
        if (component == null) {
            component = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        component.inject(this);
        return component;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        this.component = applicationComponent;
    }
}
