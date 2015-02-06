package com.nicjansma.library.net;

import org.json.JSONObject;

/**
 * JSON object interface.
 * 
 * @author Nic Jansma
 */
public interface IJsonObject
{
    /**
     * Initializes the object with a JSONObject.
     * 
     * @param json JSON object to initialize from.
     */
    void initialize(final JSONObject json);
    
    /**
     * Serializes the object to a JSONObject.
     * 
     * @return Serialized JSON object
     */
    JSONObject toJson();
}
