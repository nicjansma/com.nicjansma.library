package com.nicjansma.library.net;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A JSON object that is cacheable.
 *
 * @author Nic Jansma
 */
public abstract class CacheableJsonObjectBase
    implements ICacheableJsonObject
{
    /**
     * Date the object was retrieved.
     */
    private long _cacheDate;

    /**
     * Construct a new CacheableJsonObjectBase object.
     */
    public CacheableJsonObjectBase()
    {
        _cacheDate = new Date().getTime();
    }

    /**
     * Initializes the object with a JSONObject.
     *
     * @param json JSON object to initialize from.
     */
    @Override
    public final void initialize(final JSONObject json)
    {
        // use the date specified in the JSON (if cached), or now
        _cacheDate = json.has("cache_date") ? json.optLong("cache_date") : new Date().getTime();

        initializeInternal(json);
    }

    /**
     * Serializes the object to a JSONObject.
     *
     * @return Serialized JSON object
     */
    @Override
    public final JSONObject toJson()
    {
        JSONObject json = new JSONObject();

        try
        {
            json.put("cache_date", _cacheDate);
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
        }

        json = toJsonInternal(json);

        return json;
    }

    /**
     * Gets the age of the JSON object.
     *
     * @return Age of the JSON object (in milliseconds)
     */
    @Override
    public final long getCacheAge()
    {
        return new Date().getTime() - _cacheDate;
    }

    /**
     * Performs second-step creation of the JSON object.
     *
     * @param json JSON object
     * @return Updated JSON object
     */
    protected abstract JSONObject toJsonInternal(final JSONObject json);

    /**
     * Second-step initialization from a JSON object.
     *
     * @param json JSON object.
     */
    protected abstract void initializeInternal(final JSONObject json);
}
