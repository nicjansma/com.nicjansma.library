package com.nicjansma.library.analytics;

import android.content.Context;

/**
 * IAnalyticsTracker interface.
 *
 * @author Nic Jansma
 */
public interface IAnalyticsTracker
{
    /**
     * Initializes the interface.
     *
     * Until Init() is called, no tracking is done.
     *
     * @param context Application context
     * @param configResId Configuration res\xml ID
     */
    void init(final Context context, final int configResId);

    /**
     * Tracks a screen view.
     *
     * @param screen Screen
     */
    void trackScreenView(final String screen);

    /**
     * Tracks an event.
     *
     * @param category Category
     * @param action   Action
     * @param label    Label
     * @param value    Value
     */
    void trackEvent(final String category, final String action, final String label, final long value);

    /**
     * Tracks a User Timing.
     *
     * @param category Category
     * @param value    Value
     * @param name     Name
     * @param label    Label
     */
    void trackUserTiming(final String category, final long value, final String name, final String label);

    /**
     * Sets a custom dimension.
     *
     * @param index Dimension index
     * @param value Dimension value
     */
    void setCustomDimension(final int index, final String value);

    /**
     * Sets a custom metric.
     *
     * @param index Metric index
     * @param value Metric value
     */
    void setCustomMetric(final int index, final float value);

    /**
     * Dispatches the latest data.
     */
    void dispatch();

    /**
     * Stops the tracker.
     */
    void stop();
}
