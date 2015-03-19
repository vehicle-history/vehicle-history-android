package pl.vehicle_history;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Dawid on 2015-03-16.
 */
public class VehicleHistoryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
        PackageInfoProvider.newInstance(getApplicationContext());
    }
}
