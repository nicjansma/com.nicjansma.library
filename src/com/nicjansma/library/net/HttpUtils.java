package com.nicjansma.library.net;

import android.util.Log;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * HTTP utilities.
 *
 * @author Nic Jansma
 */
public abstract class HttpUtils
{
    //
    // Constants
    //
    /**
     * Class tag (for debugging).
     */
    private static final String TAG = HttpUtils.class.getSimpleName();

    //
    // Privates
    //
    /**
     * IHttpSimpleFetcher to use for communications.
     */
    private static IHttpSimpleFetcher _httpFetcher = null;

    /**
     * Gets the IHttpSimpleFetcher.
     *
     * If none are set, a SimpleHttpFetcher is created.
     *
     * @return IHttpSimpleFetcher
     */
    public static IHttpSimpleFetcher getHttpFetcher()
    {
        if (_httpFetcher == null)
        {
            _httpFetcher = new SimpleHttpFetcher();
        }

        return _httpFetcher;
    }

    /**
     * Sets the IHttpSimpleFetcher.
     *
     * @param httpFetcher IHttpSimpleFetcher to use
     */
    public static void setHttpFetcher(final IHttpSimpleFetcher httpFetcher)
    {
        _httpFetcher = httpFetcher;
    }

    /**
     * Gets a JSON Array from a Url.
     *
     * @param url Url to get the JSON array from.
     *
     * @return JSON Array from the Url
     */
    public static JsonResult getJson(final String url)
    {
        return getJson(url, new JsonResult());
    }

    /**
     * Gets a JSON Array from a Url.
     *
     * @param <T> JsonResult type.
     *
     * @param url Url to get the JSON array from.
     * @param json JSON object to set.
     *
     * @return JSON Array from the Url
     */
    public static final <T extends JsonResult> T getJson(final String url, final T json)
    {
        HttpSimpleResponse response = getHttpFetcher().fetch(url);
        String httpResponse = response.getResponse();

        json.setHttpResponse(response);

        if (httpResponse.length() > 0)
        {
            if (httpResponse.startsWith("["))
            {
                try
                {
                    json.setJsonArray(new JSONArray(httpResponse));
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Could not convert to JSON array.", e);
                }
            }
            else if (httpResponse.startsWith("{"))
            {
                try
                {
                    json.setJsonObject(new JSONObject(httpResponse));
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Could not convert to JSON object.", e);
                }
            }
            else if (httpResponse.startsWith("\""))
            {
                if (httpResponse.endsWith("\""))
                {
                    json.setJsonString(httpResponse.subSequence(1, httpResponse.length() - 1).toString());
                }
            }
        }

        return json;
    }

    /**
     * Gets a JSON Array from a Url.
     *
     * @param url Url to get the JSON array from.
     *
     * @return JSON Array from the Url
     */
    public static JsonResultArray<String> getJsonAsArrayOfStrings(final String url)
    {
        JsonResultArray<String> json = new JsonResultArray<String>();
        json = getJson(url, json);

        // populate the result's string array
        ArrayList<String> stringArray = json.getArray();

        // clear the string array
        stringArray.clear();

        for (int i = 0; i < json.getJsonArray().length(); i++)
        {
            String newString = null;

            try
            {
                newString = json.getJsonArray().getString(i);
            }
            catch (final JSONException e)
            {
                Log.e(TAG, "Could not get the JSON array.", e);
            }

            if (newString != null)
            {
                stringArray.add(newString);
            }
        }

        return json;
    }

    /**
     * Gets the JSON results as an array of type T.
     *
     * @param <T> Result type.
     *
     * @param url URL to get JSON result from.
     * @param newClass Class to create JSON objects as.
     *
     * @return Array of JSON results.
     */
    public static <T extends IJsonObject> JsonResultArray<T> getJsonAsArrayOf(
        final String url,
        final Class<T> newClass)
    {
        JsonResultArray<T> json = new JsonResultArray<T>();
        json = getJson(url, json);

        // populate the result's string array
        ArrayList<T> array = json.getArray();

        // clear the string array
        array.clear();

        if (json.getJsonArray() != null)
        {
            for (int i = 0; i < json.getJsonArray().length(); i++)
            {
                JSONObject jsonObject = null;

                try
                {
                    jsonObject = json.getJsonArray().getJSONObject(i);
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "getJsonAsArrayOf getJsonArray", e);
                }

                if (jsonObject != null)
                {
                    T newObject = null;

                    try
                    {
                        newObject = newClass.newInstance();
                    }
                    catch (final Exception e)
                    {
                        Log.e(TAG, "Could not create the new class instance.", e);
                    }

                    if (newObject != null)
                    {
                        newObject.initialize(jsonObject);
                        array.add(newObject);
                    }
                }
            }
        }

        return json;
    }

    /**
     * Gets the JSON results as an object of type T.
     *
     * @param <T> Result type.
     *
     * @param url URL to get JSON result from.
     * @param newClass Class to create JSON objects as.
     *
     * @return JSON object.
     */
    public static <T extends IJsonObject> JsonResultObject<T> getJsonAsObjectOf(
            final String url,
            final Class<T> newClass)
    {
        JsonResultObject<T> json = new JsonResultObject<T>();
        json = getJson(url, json);

        T newObject = null;

        if (json.getJsonObject() != null && json.getJsonObject().length() > 0)
        {
            try
            {
                newObject = newClass.newInstance();
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }

            if (newObject != null)
            {
                newObject.initialize(json.getJsonObject());
            }
        }

        json.setObject(newObject);

        return json;
    }
    
    /**
     * Gets the XML result.
     *
     * @param url URL to get XML result from.
     *
     * @return XML result.
     */
    public static XmlResult getXml(final String url)
    {
        XmlResult xml = new XmlResult();

        HttpSimpleResponse response = getHttpFetcher().fetch(url);

        xml.setHttpResponse(response);

        return xml;
    }
}
