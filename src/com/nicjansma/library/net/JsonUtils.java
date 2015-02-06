package com.nicjansma.library.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Nic Jansma
 *
 * JSON utilities
 */
public abstract class JsonUtils
{
    /**
     * Converts an integer ArrayList to a JSONArray.
     * 
     * @param intArray ArrayList of integers
     * 
     * @return Array converted to JSONArray.
     */
    public static JSONArray convertIntegerArrayToJsonArray(final ArrayList<Integer> intArray)
    {
        JSONArray jsonArray = new JSONArray();
        
        for (int i = 0; i < intArray.size(); i++)
        {
            jsonArray.put(intArray.get(i));
        }
        
        return jsonArray;
    }
    
    /**
     * Converts a long ArrayList to a JSONArray.
     * 
     * @param longArray ArrayList of longs
     * 
     * @return Array converted to JSONArray.
     */
    public static JSONArray convertLongArrayToJsonArray(final ArrayList<Long> longArray)
    {
        JSONArray jsonArray = new JSONArray();
        
        for (int i = 0; i < longArray.size(); i++)
        {
            jsonArray.put(longArray.get(i));
        }
        
        return jsonArray;
    }
    
    /**
     * Converts a string ArrayList to a JSONArray.
     * 
     * @param stringArray ArrayList of Strings
     * 
     * @return Array converted to JSONArray.
     */
    public static JSONArray convertStringArrayToJsonArray(final ArrayList<String> stringArray)
    {
        JSONArray jsonArray = new JSONArray();
        
        for (int i = 0; i < stringArray.size(); i++)
        {
            jsonArray.put(stringArray.get(i));
        }
        
        return jsonArray;
    }
    
    /**
     * Converts an object ArrayList to a JSONArray.
     * 
     * @param objectArray ArrayList of objects
     * @param <T> Type of objects to convert to JSONArray
     * 
     * @return Array converted to JSONArray.
     */
    public static <T extends IJsonObject> JSONArray convertObjectArrayToJsonArray(final ArrayList<T> objectArray)
    {
        JSONArray jsonArray = new JSONArray();
        
        for (int i = 0; i < objectArray.size(); i++)
        {
            jsonArray.put(objectArray.get(i).toJson());
        }
        
        return jsonArray;
    }
    
    /**
     * Converts a JSON string into an array of objects.
     * 
     * @param <T> Class that extends IJsonObject
     * @param jsonString JSON string
     * @param newClass New class to instantiate for the object
     * @return ArrayList of type T, or null if there was an error
     */
    public static <T extends IJsonObject> ArrayList<T> convertToArrayList(
        final String jsonString,
        final Class<T> newClass)
    {
        JSONArray jsonArray = null;
        
        try
        {
            jsonArray = new JSONArray(jsonString);
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        
        return convertToArrayList(jsonArray, newClass);
    }
    
    /**
     * Converts a JSON string into an array of strings.
     * 
     * @param jsonString JSON string
     * @return ArrayList of strings, or null if there was an error
     */
    public static ArrayList<String> convertToStringArrayList(
        final String jsonString)
    {
        ArrayList<String> array = new ArrayList<String>();
        
        if (jsonString.startsWith("["))
        {
            JSONArray jsonArray = null;
            
            try
            {
                jsonArray = new JSONArray(jsonString);
            }
            catch (final JSONException e)
            {
                e.printStackTrace();
                return null;
            }
            
            if (jsonArray != null)
            {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    String curString = null;
                    
                    try
                    {
                        curString = jsonArray.getString(i);
                    }
                    catch (final JSONException e)
                    {
                        e.printStackTrace();
                    }
                    
                    if (curString != null)
                    {
                        array.add(curString);
                    }
                }
            }
        }
        else
        {
            array.add(jsonString);
        }    
        
        return array;
    }
    
    /**
     * Converts a JSON Array into an array of objects.
     * 
     * @param <T> Class that extends IJsonObject
     * @param jsonArray JSON array
     * @param newClass New class to instantiate for the object
     * @return ArrayList of type T, or null if there was an error
     */
    public static <T extends IJsonObject> ArrayList<T> convertToArrayList(
        final JSONArray jsonArray, 
        final Class<T> newClass)
    {
        ArrayList<T> array = new ArrayList<T>();
        
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = null;
            
            try
            {
                jsonObject = jsonArray.getJSONObject(i);
            }
            catch (final JSONException e)
            {
                e.printStackTrace();
            }
            
            if (jsonObject != null)
            {
                T newObject = convertToObject(jsonObject, newClass);
                
                if (newObject != null)
                {
                    array.add(newObject);
                }
            }
        }
        
        return array;
    }    
    
    /**
     * Converts a JSON string into an object.
     * 
     * @param <T> Class that extends IJsonObject
     * @param jsonString JSON string
     * @param newClass New class to instantiate for the object
     * @return Object of type T, or null if there was an error
     */
    public static <T extends IJsonObject> T convertToObject(
        final String jsonString, 
        final Class<T> newClass)
    {
        JSONObject jsonObject = null;

        try
        {
            jsonObject = new JSONObject(jsonString);
        }
        catch (final JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        
        return convertToObject(jsonObject, newClass);
    }

    
    /**
     * Converts a JSON object into an object that implements IJsonObject.
     * 
     * @param <T> Class that extends IJsonObject
     * @param jsonObject JSON object
     * @param newClass New class to instantiate for the object
     * @return Object of type T, or null if there was an error
     */
    public static <T extends IJsonObject> T convertToObject(
        final JSONObject jsonObject, 
        final Class<T> newClass)
    {
        T newObject = null;
        
        if (jsonObject != null)
        {
            try
            {
                newObject = newClass.newInstance();
            }
            catch (final Exception e)
            {
                e.printStackTrace();
                return null;
            }

            if (newObject != null)
            {
                newObject.initialize(jsonObject);
            }
        }
        
        return newObject;
    }
}
