package pl.vehicle_history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.vehicle_history.historiapojazdu.R;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class VehicleInfoFragment extends Fragment {

    public static VehicleInfoFragment newInstance() {
        VehicleInfoFragment fragment = new VehicleInfoFragment();
        return fragment;
    }

    public VehicleInfoFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vehicle_info, container, false);
        return rootView;
    }

}
