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
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import javax.inject.Inject;

import io.vehiclehistory.BuildConfig;
import io.vehiclehistory.R;
import io.vehiclehistory.SaveSearchDelegate;
import io.vehiclehistory.Search;
import io.vehiclehistory.VehicleHistoryApp;
import io.vehiclehistory.api.model.VehicleInput;
import io.vehiclehistory.api.model.VehicleResponse;
import io.vehiclehistory.data.api.caller.GetVehicleHistoryCaller;
import io.vehiclehistory.data.api.view.VehicleHistoryMvpView;
import io.vehiclehistory.fragment.AboutFragment;
import io.vehiclehistory.fragment.FindVehicleFragment;
import io.vehiclehistory.fragment.NavigationDrawerFragment;
import io.vehiclehistory.fragment.OptionsFragment;
import io.vehiclehistory.fragment.SearchHistoryFragment;
import io.vehiclehistory.injection.component.ActivityComponent;
import io.vehiclehistory.injection.component.DaggerActivityComponent;
import io.vehiclehistory.injection.module.ActivityModule;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, VehicleHistoryMvpView {

    private NavigationDrawerFragment navigationDrawerFragment;

    private CharSequence title;

    private ProgressDialog progressDialog;

    private ActivityComponent component;

    @Inject
    protected GetVehicleHistoryCaller getVehicleHistoryCaller;

    public ActivityComponent component() {

        if (component == null) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(VehicleHistoryApp.get(this).component())
                    .build();
        }

        return component;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        setContentView(R.layout.activity_main);

        getVehicleHistoryCaller.attachView(this);

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
                getVehicleHistoryCaller.getVehicleHistory(toInput(Search.EXAMPLE_SEARCH));
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

    private VehicleInput toInput(Search search) {
        VehicleInput input = new VehicleInput();

        input.setPlate(search.getPlate());
        input.setVin(search.getVin());
        input.setFirstRegistrationDate(search.getRegistrationDate());

        return input;
    }

    private void onExceptionUi(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        setInteractionLocked(false);
    }

    @Override
    public void onGetVehicleHistoryFinished(VehicleInput request, VehicleResponse response) {
        new SaveSearchDelegate(getApplicationContext()).saveSearch(request, response);
        Intent i = new Intent(this, VehicleDataActivity.class);
        i.putExtra(VehicleDataActivity.EXTRA_VEHICLE_RESPONSE_KEY, response);

        startActivity(i);
    }

    @Override
    public void onErrorResponse(String message) {
        Context applicationContext = getApplicationContext();

        if (applicationContext != null) {
            if (TextUtils.isEmpty(message)) {
                message = getString(R.string.connection_error);
            }
            onExceptionUi(message);
        }
    }

    @Override
    public void onNoConnectionError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void onRetryError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void unableToGetTokenError() {
        onErrorResponse(getString(R.string.connection_error));
    }

    @Override
    public void startedLoadingData() {
        setInteractionLocked(true);
    }

    @Override
    public void finishedLoadingData() {
        setInteractionLocked(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getVehicleHistoryCaller.detachView();
    }
}
