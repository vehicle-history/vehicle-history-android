package pl.vehicle_history.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.DatePicker;

import pl.vehicle_history.historiapojazdu.R;

/**
 * Created by m4lysh on 2015-03-20.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_KEY_DATE_STRING = "extraKeyDay";

    private DatePicker datePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        
        LayoutInflater inflater = LayoutInflater.from(activity);
        DatePicker datePicker = (DatePicker) inflater.inflate(R.layout.date_picker, null, false);

        return new AlertDialog.Builder(activity)
                .setTitle(R.string.input_registration_date)
                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            returnDate();
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

    private void returnDate() {
        Intent i = new Intent();

        i.putExtra(EXTRA_KEY_DATE_STRING, getFormattedDate());

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }

    private String getFormattedDate() {
        //TODO return real data
        return "01.01.2000";
    }

}