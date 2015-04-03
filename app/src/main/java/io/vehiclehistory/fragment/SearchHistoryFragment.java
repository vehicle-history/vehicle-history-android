package io.vehiclehistory.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import io.vehiclehistory.PerformSearchDelegate;
import io.vehiclehistory.PerformSearchDelegate.OnSearchFinishedListener;
import io.vehiclehistory.R;
import io.vehiclehistory.Search;
import io.vehiclehistory.activity.MainActivity;
import io.vehiclehistory.adapter.HistoryAdapter;
import io.vehiclehistory.adapter.HistoryAdapter.OnHistoryItemClickListener;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.database.SearchHistoryDb;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class SearchHistoryFragment extends Fragment implements OnHistoryItemClickListener {

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

    public SearchHistoryFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_history, container, false);

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
        setInteractionLocked(true);

        new PerformSearchDelegate(getActivity()).performSearch(search, new OnSearchFinishedListener() {

            @Override
            public void onSearchFinished(VehicleResponse vehicleResponse) {
                setInteractionLocked(false);
            }

            @Override
            public void onSearchError(String message) {
                //TODO implement me
                setInteractionLocked(false);
            }
        });
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
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}
