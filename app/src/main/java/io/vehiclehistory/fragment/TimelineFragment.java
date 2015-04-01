package io.vehiclehistory.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vehiclehistory.R;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.api.model.VehicleResponse;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class TimelineFragment extends Fragment {

    public static TimelineFragment newInstance(VehicleResponse vehicleResponse) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, vehicleResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TimelineFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        return rootView;
    }

}
