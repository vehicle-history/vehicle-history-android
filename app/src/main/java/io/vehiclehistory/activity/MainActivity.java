package io.vehiclehistory.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.PerformSearchDelegate;
import io.vehiclehistory.PerformSearchDelegate.OnSearchFinishedListener;
import io.vehiclehistory.R;
import io.vehiclehistory.Search;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.fragment.AboutFragment;
import io.vehiclehistory.fragment.FindVehicleFragment;
import io.vehiclehistory.fragment.NavigationDrawerFragment;
import io.vehiclehistory.fragment.OptionsFragment;
import io.vehiclehistory.fragment.SearchHistoryFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment navigationDrawerFragment;

    private CharSequence title;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        title = getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragmentToSwitch = null;

        switch (position) {
            case 0: {
                fragmentToSwitch = FindVehicleFragment.newInstance(0);
                break;
            }
            case 1: {
                showExampleVehicleData();
                break;
            }
            case 2: {
                fragmentToSwitch = SearchHistoryFragment.newInstance(1);
                break;
            }
            case 3: {
                fragmentToSwitch = OptionsFragment.newInstance(2);
                break;
            }
            case 4: {
                showMarketAppIn();
                break;
            }
            case 5: {
                fragmentToSwitch = AboutFragment.newInstance(3);
                break;
            }
            default: {
                fragmentToSwitch = FindVehicleFragment.newInstance(0);
                break;
            }
        }

        if (fragmentToSwitch != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragmentToSwitch).commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0: {
                title = getString(R.string.title_section_find_vehicle);
                break;
            }
            case 1: {
                title = getString(R.string.title_section_history);
                break;
            }
            case 2: {
                title = getString(R.string.title_section_options);
                break;
            }
            case 3: {
                title = getString(R.string.title_section_about);
                break;
            }
            default: {
                title = getString(R.string.app_name);
                break;
            }

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void showExampleVehicleData() {
        setInteractionLocked(true);

        new PerformSearchDelegate(this).performSearch(Search.EXAMPLE_SEARCH, new OnSearchFinishedListener() {

            @Override
            public void onSearchFinished(VehicleResponse vehicleResponse) {
                setInteractionLocked(false);
            }

            @Override
            public void onSearchError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                setInteractionLocked(false);
            }
        });
    }

    private void showMarketAppIn() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                    + BuildConfig.APPLICATION_ID)));
        }
    }

    private void setInteractionLocked(boolean lock) {
        if (lock) {
            progressDialog = ProgressDialog.show(this, null, getString(R.string.searching_vehicle), true, false);
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}
