package com.nicjansma.library.android;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import com.nicjansma.library.net.ICacheableJsonObject;
import com.nicjansma.library.net.IJsonObjectCache;
import com.nicjansma.library.net.JsonUtils;

/**
 * A JSON object cache stored in the Shared Preferences.
 * 
 * @author Nic Jansma
 */
public class SharedPreferencesJsonObjectCache
    extends SharedPreferencesStringObjectCache
    implements IJsonObjectCache
{

    /**
     * Creates a SharedPreferencesJsonObjectCache.
     * 
     * @param context Application context
     */
    public SharedPreferencesJsonObjectCache(final Context context)
    {
        super(context);
    }

    /**
     * Gets a JSON object from the cache.
     * 
     * @param key Cache key
     * @param newClass New class to instantiate for the object
     * @param <T> Class that extends ICacheableJsonObject
     * @return JSON object if it exists in the cache, null if not.
     */
    public final <T extends ICacheableJsonObject> T getJson(final String key, final Class<T> newClass)
    {
        String str = get(key);
        
        if (str.equals(""))
        {
            return null;
        }
        
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(str);
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
        }
        
        if (jsonObject != null)
        {
            return JsonUtils.convertToObject(jsonObject, newClass);
        }
        
        return null;
    }

    /**
     * Gets a JSON object from the cache with a max life.
     * 
     * @param key Cache key
     * @param maxLife Maximum lifetime in milliseconds
     * @param newClass New class to instantiate for the object
     * @param <T> Class that extends ICacheableJsonObject
     * @return JSON object if it exists in the cache, null if not.
     */
    public final <T extends ICacheableJsonObject> T getJson(
        final String key, 
        final Class<T> newClass, 
        final long maxLife)
    {
        T newObject = getJson(key, newClass);
        
        if (newObject != null)
        {
            if (newObject.getCacheAge() > maxLife)
            {
                return null;
            }
        }
                
        return newObject;
    }

    /**
     * Sets a JSON object in the cache.
     * 
     * @param key Cache key
     * @param <T> Class that extends ICacheableJsonObject
     * @param obj JSON object
     */
    public final <T extends ICacheableJsonObject> void set(final String key, final T obj)
    {
        set(key, obj.toJson().toString());
    }

}
