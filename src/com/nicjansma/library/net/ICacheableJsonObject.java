package com.nicjansma.library.net;

/**
 * A Cacheable JSON object.
 * 
 * @author Nic Jansma
 */
public interface ICacheableJsonObject
    extends IJsonObject
{
    /**
     * Gets the age of the JSON object.
     * 
     * @return Age of the JSON object (in milliseconds)
     */
    long getCacheAge();
}
