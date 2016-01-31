package io.vehiclehistory.injection.component;

import dagger.Component;
import io.vehiclehistory.activity.MainActivity;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.injection.PerActivity;
import io.vehiclehistory.injection.module.ActivityModule;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(VehicleDataActivity vehicleDataActivity);

    void inject(MainActivity mainActivity);
}
