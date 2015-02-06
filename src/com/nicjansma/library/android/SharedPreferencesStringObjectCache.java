package com.nicjansma.library.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nicjansma.library.net.IStringObjectCache;

/**
 * A string object cache stored in the Shared Preferences.
 * 
 * @author Nic Jansma
 */
public class SharedPreferencesStringObjectCache
    implements IStringObjectCache
{
    /**
     * Object cache: key prefix for SharedPreferences.
     */
    public static final String CACHE_KEY_PREFIX = "cache";

    /**
     * SharedPreferences object. 
     */
    private SharedPreferences _prefs;
    
    /**
     * Creates a new SharedPreferencesStringObjectCache.
     * 
     * @param context Application context
     */
    public SharedPreferencesStringObjectCache(final Context context)
    {
        _prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    /**
     * Gets a string from the cache.
     * 
     * @param key Cache key
     * @return String if it exists in the cache, empty string ("") if not
     */
    public final String get(final String key)
    {        
        return _prefs.getString(getKey(key), "");        
    }
    
    /**
     * Remove a string from the cache.
     * 
     * @param key Cache key
     */
    public final void remove(final String key)
    {        
        SharedPreferences.Editor editor = _prefs.edit();
        editor.remove(getKey(key));
        editor.commit();        
    }
    
    /**
     * Sets a string in the cache.
     * 
     * @param key Cache key
     * @param value String value
     */
    public final void set(final String key, final String value)
    {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putString(getKey(key), value);
        editor.commit();
    }
    
    /**
     * Gets the SharedPreferences key.
     * 
     * @param key Key name
     * @return Prefixed key name
     */
    private String getKey(final String key)
    {
        return CACHE_KEY_PREFIX + "-" + key;
    }
}
