package com.nicjansma.library.net;

import java.util.ArrayList;

/**
 * @author Nic Jansma
 *
 * @param <T> Type of the result array.
 */
public class JsonResultArray<T>
    extends JsonResult
{
    /**
     * The result array.
     */
    private ArrayList<T> _array;
    
    /**
     * JsonResultArray Constructor.
     */
    public JsonResultArray()
    {
        _array = new ArrayList<T>();
    }
    
    /**
     * Sets the JSON result array.
     * 
     * @param array JSON result array to set.
     */
    public final void setArray(final ArrayList<T> array)
    {
        _array = array;
    }

    /**
     * Gets the JSON result array.
     * 
     * @return JSON result array.
     */
    public final ArrayList<T> getArray()
    {
        return _array;
    }
}
