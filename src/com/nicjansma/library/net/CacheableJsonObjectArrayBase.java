package com.nicjansma.library.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A CacheableJsonObjectBase for arrays.
 * 
 * @author Nic Jansma
 *
 * @param <T> Type of object in the array
 */
public abstract class CacheableJsonObjectArrayBase<T extends CacheableJsonObjectBase>
    extends CacheableJsonObjectBase
{
    /**
     * Array of objects.
     */
    private ArrayList<T> _array;
    
    /**
     * CacheableJsonObjectArrayBase default constructor.
     */
    public CacheableJsonObjectArrayBase()
    {
        super();
    }
    
    /**
     * CacheableJsonObjectArrayBase constructor with an input array.
     * 
     * @param array Object array.
     */
    public CacheableJsonObjectArrayBase(final ArrayList<T> array)
    {
        super();
        _array = array;
    }
    
    /**
     * Gets the class of the array object. 
     * 
     * @return Class of the array object.
     */
    protected abstract Class<T> getObjectClass();

    /**
     * Gets the object array.
     * 
     * @return Object array.
     */
    public final ArrayList<T> getArray()
    {
        return _array;
    }

    /**
     * Sets the object array.
     * 
     * @param array Object array.
     */
    public final void setArray(final ArrayList<T> array)
    {
        _array = array;
    }
    
    @Override
    public final void initializeInternal(final JSONObject json)
    {
        JSONArray jsonArray = null;
        
        try
        {
            jsonArray = json.getJSONArray("array");
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
        }
        
        if (jsonArray != null)
        {
            _array = JsonUtils.convertToArrayList(jsonArray, getObjectClass());
        }
    }

    @Override
    public final JSONObject toJsonInternal(final JSONObject json)
    {
        JSONArray jsonArray = JsonUtils.convertObjectArrayToJsonArray(_array);
                
        try
        {
            json.put("array", jsonArray);
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
        }
        
        return json;
    }
}
