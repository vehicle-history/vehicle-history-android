package pl.vehicle_history;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import pl.vehicle_history.historiapojazdu.R;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class VehicleDataActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_data_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
