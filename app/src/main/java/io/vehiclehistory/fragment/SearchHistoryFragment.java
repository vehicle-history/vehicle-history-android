package io.vehiclehistory.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import javax.inject.Inject;

import io.vehiclehistory.R;
import io.vehiclehistory.SaveSearchDelegate;
import io.vehiclehistory.Search;
import io.vehiclehistory.VehicleHistoryApp;
import io.vehiclehistory.activity.MainActivity;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.adapter.HistoryAdapter;
import io.vehiclehistory.adapter.HistoryAdapter.OnHistoryItemClickListener;
import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.data.api.caller.GetVehicleHistoryCaller;
import io.vehiclehistory.data.api.view.VehicleHistoryMvpView;
import io.vehiclehistory.database.SearchHistoryDb;
import io.vehiclehistory.injection.component.ApplicationComponent;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class SearchHistoryFragment extends Fragment implements OnHistoryItemClickListener, VehicleHistoryMvpView {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int ANIMATOR_LIST = 0;
    private static final int ANIMATOR_EMPTY_LABEL = 1;
    private ProgressDialog progressDialog;

    public static SearchHistoryFragment newInstance(int sectionNumber) {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Inject
    protected GetVehicleHistoryCaller getVehicleHistoryCaller;

    private ApplicationComponent component;

    public ApplicationComponent component() {

        if (component == null) {
            component = VehicleHistoryApp.get(getActivity()).component();
        }

        return component;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_history, container, false);

        component().inject(this);
        getVehicleHistoryCaller.attachView(this);
        setupRecyclerView(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onHistoryItemClick(Search search) {
        getVehicleHistoryCaller.getVehicleHistory(toInput(search));
    }

    private void setupRecyclerView(View rootView) {
        ViewAnimator animator = (ViewAnimator) rootView.findViewById(R.id.history_view_animator);
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.history_recycler);

        SearchHistoryDb searchHistoryDb = new SearchHistoryDb(getActivity());

        animator.setDisplayedChild(searchHistoryDb.getCount() > 0 ? ANIMATOR_LIST : ANIMATOR_EMPTY_LABEL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        HistoryAdapter historyAdapter = new HistoryAdapter(searchHistoryDb, this);
        recycler.setAdapter(historyAdapter);
    }

    private void setInteractionLocked(boolean lock) {
        if (lock) {
            progressDialog = ProgressDialog.show(getActivity(), null,
                    getActivity().getString(R.string.searching_vehicle), true, false);
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    private VehicleInput toInput(Search search) {
        VehicleInput input = new VehicleInput();

        input.setPlate(search.getPlate());
        input.setVin(search.getVin());
        input.setFirstRegistrationDate(search.getRegistrationDate());

        return input;
    }

    private void onExceptionUi(String message) {
        Context context = getActivity();

        if (context != null) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            setInteractionLocked(false);
        }
    }

    @Override
    public void onGetVehicleHistoryFinished(VehicleInput request, VehicleResponse response) {
        new SaveSearchDelegate(getActivity().getApplicationContext()).saveSearch(request, response);
        Intent i = new Intent(getActivity(), VehicleDataActivity.class);
        i.putExtra(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, response);

        getActivity().startActivity(i);
    }

    @Override
    public void onErrorResponse(String message) {
        Context applicationContext = getActivity().getApplicationContext();

        if (applicationContext != null) {
            if (TextUtils.isEmpty(message)) {
                message = getString(R.string.connection_error);
            }
            onExceptionUi(message);
        }
    }

    @Override
    public void onNoConnectionError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void onRetryError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void unableToGetTokenError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void startedLoadingData() {
        setInteractionLocked(true);
    }

    @Override
    public void finishedLoadingData() {
        setInteractionLocked(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getVehicleHistoryCaller.detachView();
    }
}
