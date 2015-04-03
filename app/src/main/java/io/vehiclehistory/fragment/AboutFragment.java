package io.vehiclehistory.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.R;
import io.vehiclehistory.activity.MainActivity;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class AboutFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String MAILTO_URI_PART = "mailto";

    private TextView applicationVersionTextView;
    private Button contactButton;

    public static AboutFragment newInstance(int sectionNumber) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AboutFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        bindViews(rootView);
        setupContactButton();
        return rootView;
    }

    private void bindViews(View rootView) {
        applicationVersionTextView = (TextView) rootView.findViewById(R.id.application_version_text_view);
        applicationVersionTextView.setText(getResources().getString(R.string.about_application_version) + " " + BuildConfig.VERSION_NAME);
        contactButton = (Button) rootView.findViewById(R.id.contact_developer_button);
    }

    private void setupContactButton() {
        contactButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        MAILTO_URI_PART, getActivity().getString(R.string.contact_email), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.app_name));
                startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.about_contact)));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
