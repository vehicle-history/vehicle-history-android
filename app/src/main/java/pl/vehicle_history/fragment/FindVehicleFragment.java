package pl.vehicle_history.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewAnimator;

import pl.vehicle_history.SaveSearchDelegate;
import pl.vehicle_history.activity.MainActivity;
import pl.vehicle_history.activity.VehicleDataActivity;
import pl.vehicle_history.api.exception.VehicleHistoryApiException;
import pl.vehicle_history.api.method.AsyncMethodExecutor;
import pl.vehicle_history.api.method.GetVehicleMethod;
import pl.vehicle_history.api.method.ResponseListener;
import pl.vehicle_history.api.method.SessionHandler;
import pl.vehicle_history.api.model.Auth;
import pl.vehicle_history.api.model.VehicleInput;
import pl.vehicle_history.api.model.VehicleResponse;
import pl.vehicle_history.historiapojazdu.R;

public class FindVehicleFragment extends Fragment {

    public static final int PICK_DATE_REQ_CODE = 101;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int ANIMATOR_BUTTON = 0;
    private static final int ANIMATOR_PROGRESS = 1;

    private static  final int UNAUTHORIZED = 401;

    private static final String DATE_PICKER_TAG = "datePickerTag";

    private final Handler handler = new Handler();
    private final AsyncMethodExecutor methodExecutor = new AsyncMethodExecutor();
    private final SessionHandler sessionHandler = new SessionHandler();

    private Button findVehicleButton;
    private ViewAnimator findVehicleAnimator;

    private EditText plateEditText;
    private EditText vinEditText;
    private EditText registrationDateEditText;

    private View pickDateButton;

    private GetVehicleMethod getVehicleMethod;

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
        setupPickDateButton();
        setupSearchButton();

        //TODO: Remove this before release.
        setupTestVehicleData();

        return rootView;
    }

    private void setupTestVehicleData() {
        plateEditText.setText("BBC12345");
        vinEditText.setText("ABC123456789DEF");
        registrationDateEditText.setText("11.03.2015");
    }

    private void bindViews(View rootView) {
        findVehicleButton = (Button) rootView.findViewById(R.id.find_vehicle_button);
        findVehicleAnimator = (ViewAnimator) rootView.findViewById(R.id.find_vehicle_animator);

        plateEditText = (EditText) rootView.findViewById(R.id.plate_edit_text);
        vinEditText = (EditText) rootView.findViewById(R.id.vin_edit_text);
        registrationDateEditText = (EditText) rootView.findViewById(R.id.registration_edit_text);

        pickDateButton = rootView.findViewById(R.id.pick_date_button);
    }

    private void setupPickDateButton() {
        pickDateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();

                datePickerFragment.setTargetFragment(FindVehicleFragment.this, PICK_DATE_REQ_CODE);

                datePickerFragment.show(fm, DATE_PICKER_TAG);
            }
        });
    }

    private void setupSearchButton() {
        findVehicleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setButtonAnimator(ANIMATOR_PROGRESS);
                setUiLocked(true);
                getVehicle();
            }
        });
    }

    private void getVehicle() {
        final VehicleInput input = getInput();

        getVehicleMethod = new GetVehicleMethod(input, sessionHandler.getSession().getAccessToken(),
                new ResponseListener<VehicleResponse>() {

            @Override
            public void onSuccess(VehicleResponse response) {
                //TODO: Save response in bundle (handler).
                setButtonAnimator(ANIMATOR_BUTTON);
                setUiLocked(false);
                Intent i = new Intent(getActivity(), VehicleDataActivity.class);
                i.putExtra(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, response);
                startActivity(i);
                new SaveSearchDelegate(getActivity()).saveSearch(input, response);
            }

            @Override
            public void onError(VehicleHistoryApiException exception) {
                if (exception != null && exception.getStatusCode() == UNAUTHORIZED) {
                    sessionHandler.getNewSession(new ResponseListener<Auth>() {
                        @Override
                        public void onSuccess(Auth response) {
                            if (!sessionHandler.getSession().getAccessToken().isEmpty()) {
                                getVehicle();
                            } else {
                                onExceptionUi("Can't get token.");
                            }
                        }

                        @Override
                        public void onError(VehicleHistoryApiException exception) {
                            onExceptionUi("Can't get vehicle.");
                        }
                    });
                } else {
                    onExceptionUi("Network error.");
                }
            }
        });
        methodExecutor.execute(getVehicleMethod);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_DATE_REQ_CODE && resultCode == Activity.RESULT_OK) {
            String date = data.getStringExtra(DatePickerFragment.EXTRA_KEY_DATE_STRING);
            registrationDateEditText.setText(date);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onExceptionUi(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
        setButtonAnimator(ANIMATOR_BUTTON);
        setUiLocked(false);
    }

    private VehicleInput getInput() {
        VehicleInput input = new VehicleInput();
        //TODO: Add validation.
        input.setPlate(plateEditText.getText().toString());
        input.setVin(vinEditText.getText().toString());
        input.setFirstRegistrationDate(registrationDateEditText.getText().toString());
        return input;
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
