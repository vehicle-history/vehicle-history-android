package io.vehiclehistory.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.DatePicker;

import io.vehiclehistory.R;

/**
 * Created by m4lysh on 2015-03-20.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_KEY_DATE_STRING = "extraKeyDay";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        
        LayoutInflater inflater = LayoutInflater.from(activity);
        final DatePicker datePicker = (DatePicker) inflater.inflate(R.layout.date_picker, null, false);

        return new AlertDialog.Builder(activity, R.style.DialogTheme)
                .setTitle(R.string.input_registration_date)
                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            returnDate(datePicker);
                        }
                    }
                )
                .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }
                )
                .setView(datePicker)
                .create();
    }

    private void returnDate(DatePicker datePicker) {
        Intent i = new Intent();

        i.putExtra(EXTRA_KEY_DATE_STRING, getFormattedDate(datePicker));

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }

    private String getFormattedDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        StringBuilder sb = new StringBuilder();

        sb.append(day < 10 ? "0" : "");
        sb.append(day);
        sb.append(".");
        sb.append(month < 10 ? "0" : "");
        sb.append(month);
        sb.append(".");
        sb.append(year);

        return sb.toString();
    }

}