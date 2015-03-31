package pl.vehicle_history;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import pl.vehicle_history.historiapojazdu.BuildConfig;

/**
 * Created by Dawid on 2015-03-16.
 */
public class VehicleHistoryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
