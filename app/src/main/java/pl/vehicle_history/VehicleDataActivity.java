package pl.vehicle_history;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

import pl.vehicle_history.fragment.TimelineFragment;
import pl.vehicle_history.fragment.VehicleInfoFragment;
import pl.vehicle_history.historiapojazdu.R;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class VehicleDataActivity extends ActionBarActivity implements TabListener {

    public enum VehicleTag {
        VEHICLE_INFO, TIMELINE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        setupTabs(actionBar);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        VehicleTag tag = (VehicleTag) tab.getTag();
        switch (tag) {
            case VEHICLE_INFO: {
                fragmentTransaction.replace(R.id.vehicle_data_container, VehicleInfoFragment.newInstance());
                break;
            }
            case TIMELINE: {
                fragmentTransaction.replace(R.id.vehicle_data_container, TimelineFragment.newInstance());
                break;
            }
            default: {
                //nop
                break;
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
        //nop
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        //nop
    }

    private void setupTabs(ActionBar actionBar) {
        Tab tab = actionBar.newTab()
                .setText(R.string.vehicle_info)
                .setTag(VehicleTag.VEHICLE_INFO)
                .setTabListener(this);

        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.timeline)
                .setTag(VehicleTag.TIMELINE)
                .setTabListener(this);

        actionBar.addTab(tab);
    }

}
