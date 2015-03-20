package pl.vehicle_history.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
                            //TODO return date
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

}