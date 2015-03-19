package pl.vehicle_history;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Dawid on 2015-03-19.
 */
public class PackageInfoProvider {

    private static final String DEFAULT_PACKAGE_VERSION = "unknown";

    private Context context;
    private static PackageInfoProvider packageInfoProvider;

    private PackageInfoProvider(Context context) {
        this.context = context;
    }

    public static PackageInfoProvider getInstance()
    {
        if (packageInfoProvider == null)
        {
            throw new IllegalArgumentException("Initialize instance with context first.");
        }
        return packageInfoProvider;
    }

    public static PackageInfoProvider newInstance(Context context) {
        packageInfoProvider = new PackageInfoProvider(context);
        return getInstance();
    }

    public String getPackageVersion() {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return DEFAULT_PACKAGE_VERSION;
        }
    }
}
