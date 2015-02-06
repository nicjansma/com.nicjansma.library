package com.nicjansma.library.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Android utilities.
 *
 * @author Nic Jansma
 */
public abstract class AndroidUtils
{
    /**
     * Resource type of drawable.
     */
    public static final String RESOURCE_DRAWABLE = "drawable";

    /**
     * Resource type of string.
     */
    public static final String RESOURCE_STRING = "string";

    /**
     * Private constructor.
     */
    private AndroidUtils()
    {
    }

    /**
     * Retrieves the resource ID from a string (for example, a drawable from the
     * image name).
     *
     * @param context Application or Activity context
     * @param type Resource type
     * @param resourceName Resource's name
     *
     * @return Resource ID of the named resource
     */
    public static int resourceIdFromString(final Context context, final String type, final String resourceName)
    {
        return context.getResources().getIdentifier(resourceName, type, context.getPackageName());
    }

    /**
     * Launches to the specific app in the Android Marketplace.
     *
     * @param context Application or Activity context
     * @param action Desired action
     */
    public static void launchToMarketplace(final Context context, final String action)
    {
        final Uri intentUri = Uri.parse("market://search?q=pname:" + action);

        final Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);

        context.startActivity(intent);
    }

    /**
     * Launches a browser to the specified URL.
     *
     * @param context Android context
     * @param url URL to browse to
     */
    public static void launchToUrl(final Context context, final Uri url)
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW, url);
        context.startActivity(intent);
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     *
     * @param context The application's environment
     * @param action The Intent action to check for availability
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise
     */
    public static boolean isIntentAvailable(final Context context, final String action)
    {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * Determines if the app is running in the Android emulator.
     *
     * http://stackoverflow.com/questions/2799097/ ->
     *   how-can-i-detect-when-an-android-application-is-running-in-the-emulator
     *
     * @return True if running in the emulator.
     */
    public static boolean isRunningInEmulator()
    {
        return Build.PRODUCT.equals("google_sdk")
               || Build.PRODUCT.equals("sdk")
               || Build.PRODUCT.equals("full_x86")
               || Build.FINGERPRINT.contains("generic");
    }

    /**
     * Hides the soft keyboard.
     *
     * @param context Application context
     * @param windowToken Window token
     */
    public static void hideSoftKeyboard(final Context context, final IBinder windowToken)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    /**
     * Gets the SDK_INT version code, available in SDK 4 and higher.
     *
     * @return SDK_INT version code.
     */
    public static int getSdkIntInternal()
    {
        return Build.VERSION.SDK_INT;
    }

    /**
     * "Exits" the app to the Home screen".
     * @param context Application context
     */
    public static void exitToHome(final Context context)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
