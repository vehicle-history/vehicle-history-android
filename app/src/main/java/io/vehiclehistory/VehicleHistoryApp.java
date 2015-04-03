package io.vehiclehistory;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Dawid on 2015-03-16.
 */
public class VehicleHistoryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
