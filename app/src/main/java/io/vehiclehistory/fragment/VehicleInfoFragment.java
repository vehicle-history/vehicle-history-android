package io.vehiclehistory.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.vehiclehistory.R;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.api.OptionalProvider;
import io.vehiclehistory.api.model.Vehicle;
import io.vehiclehistory.api.model.VehicleResponse;

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
        OptionalProvider optionalProvider = new OptionalProvider(getActivity());

        TextView carLabel = (TextView) rootView.findViewById(R.id.car_label);
        carLabel.setText(vehicle.getName().getMake() + " " + vehicle.getName().getModel());

        TextView producer = (TextView) rootView.findViewById(R.id.producer);
        producer.setText(optionalProvider.getMake(vehicle));

        TextView model = (TextView) rootView.findViewById(R.id.model);
        model.setText(optionalProvider.getModel(vehicle));

        TextView year = (TextView) rootView.findViewById(R.id.year);
        year.setText(optionalProvider.getProductionYear(vehicle));

        TextView regNumber = (TextView) rootView.findViewById(R.id.reg_number);
        regNumber.setText(optionalProvider.getPlate(vehicle));

        TextView vin = (TextView) rootView.findViewById(R.id.vin);
        vin.setText(optionalProvider.getVin(vehicle));

        TextView firstRegistration = (TextView) rootView.findViewById(R.id.first_registration);
        firstRegistration.setText(optionalProvider.getFirstVehicleRegistration(vehicle));

        TextView engineCapacity = (TextView) rootView.findViewById(R.id.engine_capacity);
        engineCapacity.setText(optionalProvider.getCubicCapacity(vehicle));

        TextView fuelType = (TextView) rootView.findViewById(R.id.fuel_type);
        fuelType.setText(optionalProvider.getFuelType(vehicle));

        TextView mileage = (TextView) rootView.findViewById(R.id.mileage);
        mileage.setText(optionalProvider.getMileage(vehicle));

        TextView insurance = (TextView) rootView.findViewById(R.id.insurance);
        insurance.setText(optionalProvider.hasInsurance(vehicle));

        TextView registrationStatus = (TextView) rootView.findViewById(R.id.registration_status);
        registrationStatus.setText(optionalProvider.getRegistrationStatus(vehicle));

        TextView technicalExamination = (TextView) rootView.findViewById(R.id.technical_examination);
        technicalExamination.setText(optionalProvider.getInspectionStatus(vehicle));

        TextView vehicleStolen = (TextView) rootView.findViewById(R.id.vehicle_stolen);
        vehicleStolen.setText(vehicle.getStolen() ?
                getActivity().getString(R.string.yes) : getActivity().getString(R.string.no));

        TextView vehicleClass = (TextView) rootView.findViewById(R.id.vehicle_class);
        vehicleClass.setText(optionalProvider.getVehicleKind(vehicle));

        TextView vehicleType = (TextView) rootView.findViewById(R.id.vehicle_type);
        vehicleType.setText(optionalProvider.getVehicleType(vehicle));
    }

}
