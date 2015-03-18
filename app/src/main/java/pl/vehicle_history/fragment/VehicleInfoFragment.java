package pl.vehicle_history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.vehicle_history.VehicleDataActivity;
import pl.vehicle_history.api.model.Vehicle;
import pl.vehicle_history.api.model.VehicleResponse;
import pl.vehicle_history.historiapojazdu.R;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class VehicleInfoFragment extends Fragment {

    public static VehicleInfoFragment newInstance(VehicleResponse vehicleResponse) {
        VehicleInfoFragment fragment = new VehicleInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, vehicleResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    public VehicleInfoFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vehicle_info, container, false);

        Bundle args = getArguments();
        VehicleResponse vehicleResponse = (VehicleResponse) args.getSerializable(
                VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY);

        bindDataToView(vehicleResponse.getVehicle(), rootView);

        return rootView;
    }

    private void bindDataToView(Vehicle vehicle, View rootView) {
        TextView carLabel = (TextView) rootView.findViewById(R.id.car_label);
        carLabel.setText(vehicle.getName().getMake() + " " + vehicle.getName().getModel());

        TextView producer = (TextView) rootView.findViewById(R.id.producer);
        producer.setText(vehicle.getName().getMake().toString());

        TextView model = (TextView) rootView.findViewById(R.id.model);
        model.setText(vehicle.getName().getModel());

        TextView year = (TextView) rootView.findViewById(R.id.year);
        year.setText(vehicle.getProduction() != null ? vehicle.getProduction().getYear() : "");

        TextView reg_number = (TextView) rootView.findViewById(R.id.reg_number);
        reg_number.setText(vehicle.getPlate().getValue());

        TextView vin = (TextView) rootView.findViewById(R.id.vin);
        vin.setText(vehicle.getVin());

        TextView first_registration = (TextView) rootView.findViewById(R.id.first_registration);
        first_registration.setText(vehicle.getRegistration().getFirstAt());

        TextView engine_capacity = (TextView) rootView.findViewById(R.id.engine_capacity);
        engine_capacity.setText(vehicle.getEngine().getCubicCapacity());

        TextView fuel_type = (TextView) rootView.findViewById(R.id.fuel_type);
        fuel_type.setText(vehicle.getEngine().getFuel().toString());

        TextView mileage = (TextView) rootView.findViewById(R.id.mileage);
        mileage.setText(vehicle.getMileage() != null ?
                vehicle.getMileage().getValue() : getActivity().getString(R.string.no_data));

        TextView insurance = (TextView) rootView.findViewById(R.id.insurance);
        insurance.setText(vehicle.getPolicy().getStatus().toString());

        TextView registration_status = (TextView) rootView.findViewById(R.id.registration_status);
        registration_status.setText(vehicle.getRegistration().getStatus().toString());

        TextView technical_examination = (TextView) rootView.findViewById(R.id.technical_examination);
        technical_examination.setText(vehicle.getInspection().getStatus().toString());

        TextView vehicle_stolen = (TextView) rootView.findViewById(R.id.vehicle_stolen);
        vehicle_stolen.setText(vehicle.getStolen() ?
                getActivity().getString(R.string.yes) : getActivity().getString(R.string.no));

        TextView vehicle_class = (TextView) rootView.findViewById(R.id.vehicle_class);
        vehicle_class.setText(vehicle.getType().getKind().toString());

        TextView vehicle_type = (TextView) rootView.findViewById(R.id.vehicle_type);
        vehicle_type.setText(vehicle.getType().getType().toString());
    }

}
