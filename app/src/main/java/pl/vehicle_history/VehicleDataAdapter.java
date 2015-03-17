package pl.vehicle_history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.vehicle_history.api.model.VehicleResponse;
import pl.vehicle_history.fragment.TimelineFragment;
import pl.vehicle_history.fragment.VehicleInfoFragment;

/**
 * Created by m4lysh on 2015-03-17.
 */
public class VehicleDataAdapter extends FragmentPagerAdapter {

    private static final int VEHICLE_INFO_POS = 0;
    private static final int TIMELINE_POS = 1;

    private VehicleResponse vehicleResponse;

    public VehicleDataAdapter(FragmentManager fm, VehicleResponse vehicleResponse) {
        super(fm);
        this.vehicleResponse = vehicleResponse;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        if (VEHICLE_INFO_POS == i) {
            fragment = VehicleInfoFragment.newInstance(vehicleResponse);
        } else if (TIMELINE_POS == i) {
            fragment = TimelineFragment.newInstance(vehicleResponse);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
