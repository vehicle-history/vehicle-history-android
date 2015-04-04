package io.vehiclehistory.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import io.vehiclehistory.R;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.api.model.Event;
import io.vehiclehistory.api.model.VehicleResponse;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class TimelineFragment extends Fragment {

    public static TimelineFragment newInstance(VehicleResponse vehicleResponse) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, vehicleResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TimelineFragment() {
        //nop
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        Bundle args = getArguments();
        VehicleResponse vehicleResponse = (VehicleResponse) args.getSerializable(
                VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY);

        bindDataToView(vehicleResponse.getEvents(), rootView);

        return rootView;
    }

    private void bindDataToView(List<Event> eventList, View rootView) {
        final TableLayout table = (TableLayout) rootView.findViewById(R.id.timeline_table_layout);

        for (Event event : eventList) {
            addRow(table, event);
        }
    }

    private void addRow(TableLayout table, Event event) {
        View row = LayoutInflater.from(getActivity()).inflate(R.layout.timeline_row, table, false);

        TextView dateTextView = (TextView) row.findViewById(R.id.timeline_date);
        dateTextView.setText(event.getCreatedAt());

        TextView eventNameTextView = (TextView) row.findViewById(R.id.timeline_description);
        //TODO: enum -> resources string value.
        eventNameTextView.setText(event.getType().toString());

        table.addView(row);
    }
}
