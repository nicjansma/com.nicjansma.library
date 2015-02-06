package com.nicjansma.library.net;

/**
 * A string object cache interface.
 * 
 * @author Nic Jansma
 */
public interface IStringObjectCache
{
    /**
     * Gets a string from the cache.
     * 
     * @param key Cache key
     * @return String if it exists in the cache, empty string ("") if not
     */
    String get(final String key);

    /**
     * Sets a string in the cache.
     * 
     * @param key Cache key
     * @param value String value
     */
    void set(final String key, final String value);

    /**
     * Remove a string from the cache.
     * 
     * @param key Cache key
     */
    void remove(final String key);
}
