package pl.vehicle_history.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.vehicle_history.activity.MainActivity;
import pl.vehicle_history.adapter.HistoryAdapter;
import pl.vehicle_history.database.SearchHistoryDb;
import pl.vehicle_history.historiapojazdu.R;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class SearchHistoryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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

    private void setupRecyclerView(View rootView) {
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.history_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        HistoryAdapter historyAdapter = new HistoryAdapter(new SearchHistoryDb(getActivity()));
        recycler.setAdapter(historyAdapter);
    }



}
