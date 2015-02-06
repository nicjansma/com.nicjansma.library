package com.nicjansma.library.net;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a JSON result.
 * 
 * @author Nic Jansma
 */
public class JsonResult 
    extends HttpResultBase
{
    /**
     * The JSON result (object). 
     */
    private JSONObject _jsonObject = null;
    
    /**
     * The JSON result (array).
     */
    private JSONArray _jsonArray = null;
    
    /**
     * The JSON result (string).
     */
    private String _jsonString = null;
    
    /**
     * Sets the JSON result as an object.
     * 
     * @param jsonObject The JSON object.
     */
    public final void setJsonObject(final JSONObject jsonObject)
    {
        _jsonObject = jsonObject;
    }
    
    /**
     * Gets the JSON result as an object. 
     * 
     * @return The JSON object.
     */
    public final JSONObject getJsonObject()
    {
        return _jsonObject;
    }
    
    /**
     * Sets the JSON result as an array.
     * 
     * @param jsonArray The JSON array.
     */
    public final void setJsonArray(final JSONArray jsonArray)
    {
        _jsonArray = jsonArray;
    }
    
    /**
     * Gets the JSON result as an array.
     * 
     * @return The JSON array.
     */
    public final JSONArray getJsonArray()
    {
        return _jsonArray;
    }
    
    /**
     * Sets the JSON result as a string.
     * 
     * @param str The JSON string.
     */
    public final void setJsonString(final String str)
    {
        _jsonString = str;
    }
    
    /**
     * Gets the JSON result as a string. 
     * 
     * @return The JSON string.
     */
    public final String getJsonString()
    {
        return _jsonString;
    }
}
