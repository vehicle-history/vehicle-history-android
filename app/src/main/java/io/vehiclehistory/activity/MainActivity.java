package io.vehiclehistory.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.R;
import io.vehiclehistory.fragment.AboutFragment;
import io.vehiclehistory.fragment.FindVehicleFragment;
import io.vehiclehistory.fragment.NavigationDrawerFragment;
import io.vehiclehistory.fragment.OptionsFragment;
import io.vehiclehistory.fragment.SearchHistoryFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        title = getTitle();

        // Set up the drawer.
        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragmentToSwitch = null;

        switch (position) {
            case 0: {
                fragmentToSwitch = FindVehicleFragment.newInstance(0);
                break;
            }
            case 1: {
                fragmentToSwitch = SearchHistoryFragment.newInstance(1);
                break;
            }
            case 2: {
                fragmentToSwitch = OptionsFragment.newInstance(2);
                break;
            }
            case 3: {
                showMarketAppIn();
                break;
            }
            case 4: {
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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void showMarketAppIn() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="
                    + BuildConfig.APPLICATION_ID)));
        }
    }
}
