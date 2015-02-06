package com.nicjansma.library.analytics;

import android.content.Context;

import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.HitBuilders.TimingBuilder;
import com.google.android.gms.analytics.Tracker;

/**
 * Default Analytics tracker.
 */
public abstract class GoogleAnalyticsTrackerBase
    implements IAnalyticsTracker
{
    /**
     * Google Analytics tracker.
     */
    private Tracker _tracker;

    /**
     * Application context.
     */
    private Context _context;

    /**
     * Custom dimensions.
     */
    private final HashMap<Integer, String> _dimensions = new HashMap<Integer, String>();

    /**
     * Custom metrics.
     */
    private final HashMap<Integer, Float> _metrics = new HashMap<Integer, Float>();

    /**
     * Inherited classes must implement this.
     *
     * Called during init(), after the tracker is setup
     *
     * @param context Application context
     */
    protected abstract void initInternal(final Context context);

    /**
     * GoogleAnalyticsTrackerBase constructor.
     */
    public GoogleAnalyticsTrackerBase()
    {
    }

    /**
     * Gets the GA Tracker.
     *
     * @return Google Analytics Tracker
     */
    protected final Tracker getTracker()
    {
        return _tracker;
    }

    @Override
    public final void init(final Context context, final int configResId)
    {
        _context = context;

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        _tracker = analytics.newTracker(configResId);

        if (_tracker == null)
        {
            return;
        }

        // Enable Advertising Features.
        _tracker.enableAdvertisingIdCollection(true);
        _tracker.enableExceptionReporting(true);

        // initialize all inherited classes
        initInternal(context);
    }

    @Override
    public final void trackScreenView(final String page)
    {
        if (_tracker == null)
        {
            return;
        }

        _tracker.setScreenName(page);
        _tracker.send(getTrackerForScreenView()
            .build());
    }

    @Override
    public final void trackEvent(
        final String category,
        final String action,
        final String label,
        final long value)
    {
        if (_tracker == null)
        {
            return;
        }

        _tracker.send(getTrackerForEvent()
            .setCategory(category)
            .setAction(action)
            .setLabel(label)
            .setValue(value)
            .build());
    }

    @Override
    public final void trackUserTiming(
            final String category,
            final long value,
            final String name,
            final String label)
    {
        if (_tracker == null)
        {
            return;
        }

        _tracker.send(getTrackerForTiming()
                      .setCategory(category)
                      .setValue(value)
                      .setVariable(name)
                      .setLabel(label)
                      .build());
    }

    @Override
    public final void setCustomDimension(final int index, final String value)
    {
        _dimensions.put(index, value);
    }

    @Override
    public final void setCustomMetric(final int index, final float value)
    {
        _metrics.put(index, value);
    }

    /**
     * Builds a ScreenView tracker.
     *
     * Note this can't be genericized because of the Google Analytics builder interface
     *
     * @return ScreenView builder
     */
    private ScreenViewBuilder getTrackerForScreenView()
    {
        ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();

        for (Integer index : _dimensions.keySet())
        {
            builder.setCustomDimension(index, _dimensions.get(index));
        }

        for (Integer index : _metrics.keySet())
        {
            builder.setCustomMetric(index, _metrics.get(index));
        }

        return builder;
    }

    /**
     * Builds a Event tracker.
     *
     * Note this can't be genericized because of the Google Analytics builder interface
     *
     * @return Event builder
     */
    private EventBuilder getTrackerForEvent()
    {
        EventBuilder builder = new HitBuilders.EventBuilder();

        for (Integer index : _dimensions.keySet())
        {
            builder.setCustomDimension(index, _dimensions.get(index));
        }

        for (Integer index : _metrics.keySet())
        {
            builder.setCustomMetric(index, _metrics.get(index));
        }

        return builder;
    }

    /**
     * Builds a Timing tracker.
     *
     * Note this can't be genericized because of the Google Analytics builder interface
     *
     * @return Event builder
     */
    private TimingBuilder getTrackerForTiming()
    {
        TimingBuilder builder = new HitBuilders.TimingBuilder();

        for (Integer index : _dimensions.keySet())
        {
            builder.setCustomDimension(index, _dimensions.get(index));
        }

        for (Integer index : _metrics.keySet())
        {
            builder.setCustomMetric(index, _metrics.get(index));
        }

        return builder;
    }

    @Override
    public final void dispatch()
    {
        if (_tracker == null)
        {
            return;
        }

        GoogleAnalytics.getInstance(_context).dispatchLocalHits();
    }

    @Override
    public final void stop()
    {
        _tracker = null;
    }
}
