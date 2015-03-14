package pl.vehicle_history.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewAnimator;

import pl.vehicle_history.MainActivity;
import pl.vehicle_history.VehicleDataActivity;
import pl.vehicle_history.api.MockMethodDelegate;
import pl.vehicle_history.api.MockMethodDelegate.OnExecutionFinishedListener;
import pl.vehicle_history.historiapojazdu.R;

public class FindVehicleFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int ANIMATOR_BUTTON = 0;
    private static final int ANIMATOR_PROGRESS = 1;

    private final Handler handler = new Handler();

    private Button findVehicleButton;
    private ViewAnimator findVehicleAnimator;

    private EditText plateEditText;
    private EditText vinEditText;
    private EditText registrationDateEditText;

    public static FindVehicleFragment newInstance(int sectionNumber) {
        FindVehicleFragment fragment = new FindVehicleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FindVehicleFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_vehicle, container, false);

        bindViews(rootView);
        setupButton();

        return rootView;
    }

    private void bindViews(View rootView) {
        findVehicleButton = (Button) rootView.findViewById(R.id.find_vehicle_button);
        findVehicleAnimator = (ViewAnimator) rootView.findViewById(R.id.find_vehicle_animator);

        plateEditText = (EditText) rootView.findViewById(R.id.plate_edit_text);
        vinEditText = (EditText) rootView.findViewById(R.id.vin_edit_text);
        registrationDateEditText = (EditText) rootView.findViewById(R.id.registration_edit_text);
    }

    private void setupButton() {
        findVehicleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setButtonAnimator(ANIMATOR_PROGRESS);
                setUiLocked(true);

                new MockMethodDelegate().execute(new OnExecutionFinishedListener() {

                    @Override
                    public void onExecutionFinished() {
                        setButtonAnimator(ANIMATOR_BUTTON);
                        setUiLocked(false);
                        Intent i = new Intent(getActivity(), VehicleDataActivity.class);
                        startActivity(i);
                    }
                });
            }
        });
    }

    private void setUiLocked(final boolean locked) {
        this.handler.post(new Runnable() {

            @Override
            public void run() {
                plateEditText.setEnabled(!locked);
                vinEditText.setEnabled(!locked);
                registrationDateEditText.setEnabled(!locked);
            }
        });
    }

    private void setButtonAnimator(final int childPosition) {
        this.handler.post(new Runnable() {

            @Override
            public void run() {
                findVehicleAnimator.setDisplayedChild(childPosition);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
