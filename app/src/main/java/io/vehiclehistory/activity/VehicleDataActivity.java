package io.vehiclehistory.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

import io.vehiclehistory.R;
import io.vehiclehistory.adapter.VehicleDataAdapter;
import io.vehiclehistory.api.model.VehicleResponse;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class VehicleDataActivity extends ActionBarActivity implements TabListener {

    public static final String EXTRA_VEHICLE_RESPONSE_KEY = "extra_vehicle";

    private ViewPager pager;
    private VehicleDataAdapter vehicleDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_data);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        pager = (ViewPager) findViewById(R.id.vehicle_data_pager);

        VehicleResponse vehicle = (VehicleResponse) getIntent().getSerializableExtra(
                EXTRA_VEHICLE_RESPONSE_KEY);

        vehicleDataAdapter = new VehicleDataAdapter(getFragmentManager(), vehicle);

        setupViewPager(actionBar);
        setupTabs(actionBar);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
        //nop
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        //nop
    }

    private void setupViewPager(final ActionBar actionBar) {
        pager.setAdapter(vehicleDataAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
    }

    private void setupTabs(ActionBar actionBar) {
        Tab tab = actionBar.newTab()
                .setText(R.string.vehicle_info)
                .setTabListener(this);

        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.timeline)
                .setTabListener(this);

        actionBar.addTab(tab);
    }

}
