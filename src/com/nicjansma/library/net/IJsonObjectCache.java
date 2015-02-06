package com.nicjansma.library.net;


/**
 * A JSON object cache interface.
 * 
 * @author Nic Jansma
 */
public interface IJsonObjectCache
    extends IStringObjectCache
{
    /**
     * Gets a JSON object from the cache.
     * 
     * @param key Cache key
     * @param newClass New class to instantiate for the object
     * @param <T> Class that extends ICacheableJsonObject
     * @return JSON object if it exists in the cache, null if not.
     */
    <T extends ICacheableJsonObject> T getJson(final String key, final Class<T> newClass);

    /**
     * Gets a JSON object from the cache with a max life.
     * 
     * @param key Cache key
     * @param maxLife Maximum lifetime in milliseconds
     * @param newClass New class to instantiate for the object
     * @param <T> Class that extends ICacheableJsonObject
     * @return JSON object if it exists in the cache, null if not.
     */
    <T extends ICacheableJsonObject> T getJson(final String key, final Class<T> newClass, final long maxLife);

    /**
     * Sets a JSON object in the cache.
     * 
     * @param key Cache key
     * @param <T> Class that extends ICacheableJsonObject
     * @param obj JSON object
     */
    <T extends ICacheableJsonObject> void set(final String key, final T obj);
}
