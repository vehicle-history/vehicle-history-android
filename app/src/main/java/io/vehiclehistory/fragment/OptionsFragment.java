package io.vehiclehistory.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import io.vehiclehistory.R;
import io.vehiclehistory.activity.MainActivity;
import io.vehiclehistory.database.SearchHistoryDb;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class OptionsFragment extends PreferenceFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static OptionsFragment newInstance(int sectionNumber) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public OptionsFragment() {
        //nop
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference clearHistory = findPreference(getString(R.string.clear_history));
        clearHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                new SearchHistoryDb(getActivity()).clearHistory();
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
