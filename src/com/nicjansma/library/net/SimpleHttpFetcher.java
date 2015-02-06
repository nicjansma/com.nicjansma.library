package com.nicjansma.library.net;

import android.util.Log;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.nicjansma.library.io.IOUtils;

/**
 * Simple HTTP fetcher.
 */
public final class SimpleHttpFetcher
    implements IHttpSimpleFetcher
{
    /**
     * Class tag (for debugging).
     */
    private static final String TAG = SimpleHttpFetcher.class.getSimpleName();

    //
    // Constants
    //
    /**
     * Default timeout (in milliseconds) of HTTP responses.
     */
    private static final int HTTP_TIMEOUT_MS = 30000;

    @Override
    public HttpSimpleResponse fetch(final String url)
    {
        String stringResult = "";
        int httpResponseCode = 0;

        // default socket timeouts
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT_MS);

        // construct client
        HttpClient httpClient = HttpsWildcardVerifier.getHttpsWildcardTolerantClient(httpParams);

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;

        try
        {
            Log.i(TAG, url);

            // query the server
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();

            if (statusLine != null)
            {
                httpResponseCode = statusLine.getStatusCode();
            }

            if (entity != null)
            {
                InputStream instream = entity.getContent();

                // convert input stream to a string
                stringResult = IOUtils.convertStreamToString(instream);

                // trim trailing newlines
                stringResult = stringResult.trim();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return HttpSimpleResponse.createErrorResponse();
        }

        return new HttpSimpleResponse(stringResult, httpResponseCode);
    }

}
