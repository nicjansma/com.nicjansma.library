package com.nicjansma.library.net;

/**
 * @author Nic Jansma
 *
 * @param <T> Result object type.
 */
public class JsonResultObject<T>
    extends JsonResult
{
    /**
     * JSON result object.
     */
    private T _obj = null;
    
    /**
     * Sets the JSON result object.
     * 
     * @param obj JSON result object.
     */
    public final void setObject(final T obj)
    {
        _obj = obj;
    }

    /**
     * Gets the JSON result object.
     * 
     * @return JSON result object.
     */
    public final T getObject()
    {
        return _obj;
    }
}
