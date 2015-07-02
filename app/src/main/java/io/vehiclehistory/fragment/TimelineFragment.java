package io.vehiclehistory.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.vehiclehistory.DateFormatter;
import io.vehiclehistory.EventColorProvider;
import io.vehiclehistory.R;
import io.vehiclehistory.activity.VehicleDataActivity;
import io.vehiclehistory.api.model.Event;
import io.vehiclehistory.api.model.VehicleResponse;

import static io.vehiclehistory.api.model.EventType.REGISTRATION;
import static io.vehiclehistory.api.model.OwnerType.UNKNOWN;

public class TimelineFragment extends Fragment {

    private final EventColorProvider eventColorProvider = new EventColorProvider();

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
        final ViewGroup layout = (ViewGroup) rootView.findViewById(R.id.timeline_layout);

        for (Event event : eventList) {
            addRow(layout, event);
        }
    }

    private void addRow(ViewGroup layout, Event event) {
        View row = LayoutInflater.from(getActivity()).inflate(R.layout.timeline_row, layout, false);

        TextView eventNameTextView = (TextView) row.findViewById(R.id.timeline_description);
        eventNameTextView.setText(getString(event.getType().getValueResource()));

        String extras = buildExtras(event);

        if (!extras.isEmpty()) {
            TextView eventExtrasTextView = (TextView) row.findViewById(R.id.timeline_extras);
            eventExtrasTextView.setVisibility(View.VISIBLE);
            eventExtrasTextView.setText(extras);
        }

        TextView dateTextView = (TextView) row.findViewById(R.id.timeline_date);
        dateTextView.setText(new DateFormatter().formatDateFromApi(event.getCreatedAt()));

        View circle = row.findViewById(R.id.timeline_circle);
        circle.setBackgroundResource(eventColorProvider.getBackground(event.getType()));

        layout.addView(row);
    }

    private String buildExtras(Event event) {
        StringBuilder stringBuilder = new StringBuilder();

        //REGISTRATION
        if (event.isAbroadRegistration() != null
                && event.isAbroadRegistration()
                && REGISTRATION == event.getType()) {

            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder.append(getActivity().getString(R.string.abroad));
        }

        //CHANGE_OWNER
        if (event.isFirstOwner() != null && event.isFirstOwner()) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder.append(getActivity().getString(R.string.first_owner));
        }

        //CHANGE_OWNER
        if (event.getLocation() != null) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder
                    .append(event.getLocation().getState())
                    .append(", ")
                    .append(event.getLocation().getCountry());
        }

        //CHANGE_OWNER or CO_OWNER
        if (event.getOwnerType() != null && UNKNOWN != event.getOwnerType()) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder.append(getString(event.getOwnerType().getValueResource()));
        }

        //DEREGISTRATION
        if (event.getNote() != null) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder.append(event.getNote());
        }

        //INSPECTION
        if (event.getExpireAt() != null) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder
                    .append(getActivity().getString(R.string.expires_at_semicolon))
                    .append(new DateFormatter().formatDateFromApi(event.getExpireAt()));
        }

        //INSPECTION
        if (event.getMileage() != null) {
            appendNewLineIfNotEmpty(stringBuilder);
            stringBuilder
                    .append(getActivity().getString(R.string.mileage_semicolon))
                    .append(event.getMileage().getValue());
        }

        return stringBuilder.toString();
    }

    private void appendNewLineIfNotEmpty(StringBuilder stringBuilder) {
        if (!stringBuilder.toString().isEmpty()) {
            stringBuilder.append("\n");
        }
    }
}
