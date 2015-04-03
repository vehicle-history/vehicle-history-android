package io.vehiclehistory.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.fragment.TimelineFragment;
import io.vehiclehistory.fragment.VehicleInfoFragment;

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
