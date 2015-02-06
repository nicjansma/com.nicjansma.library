package com.nicjansma.library.android;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Device Utilities.
 * 
 * @author Nic
 */
public abstract class DeviceUtils
{
    /**
     * Determines if the device has a camera.
     * 
     * @param context Application context
     * 
     * @return True if the device has a camera
     */
    public static boolean hasCamera(final Context context)
    {
        try
        {
            android.hardware.Camera camera = android.hardware.Camera.open();
            camera.release();
        } 
        catch (final RuntimeException e) 
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets the device's display information.
     * 
     * @param context Application context
     * 
     * @return Display information
     */
    public static Display getDisplay(final Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }
}
