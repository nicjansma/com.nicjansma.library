package com.nicjansma.library.net;

import android.util.Log;

import javax.net.ssl.SSLException;

import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

/**
 * @author Nic Jansma
 *
 * Allows certificates signed for *.example.com to also work for just example.com.
 * 
 * Todoist.com, for example, has this issue.
 * 
 * The wildcard does not validate on Android 1.5, 1.6 or 2.1.  However, it works on 2.2.
 *
 * From a solution on StackOverflow:
 * http://stackoverflow.com/questions/3135679/android-httpclient-hostname-in-certificate-didnt-match-example-com-ex
 */
public final class HttpsWildcardVerifier extends AbstractVerifier
{
    /**
     * Class tag (for debugging).
     */
    private static final String TAG = HttpsWildcardVerifier.class.getSimpleName();

    /**
     * Host name verifier delegate.
     */
    private final X509HostnameVerifier _delegate;

    /**
     * HttpsWildcardVerifier constructor.
     * 
     * @param delegate Hostname verifier delegate.
     */
    public HttpsWildcardVerifier(final X509HostnameVerifier delegate)
    {
        this._delegate = delegate;
    }

    @Override
    public void verify(final String host, final String[] cnames, final String[] subjectAlts) throws SSLException
    {
        boolean ok = false;
        
        try
        {
            _delegate.verify(host, cnames, subjectAlts);
        }
        catch (final SSLException e)
        {
            // if the host starts with a wildcard, allow if this host is the subdomain of that wildcard.
            for (String cname : cnames)
            {
                if (cname.startsWith("*."))
                {
                    try
                    {
                        _delegate.verify(
                                        host, 
                                        new String[] {
                                                cname.substring(2) 
                                        }, subjectAlts);
                        
                        ok = true;
                    }
                    catch (final Exception ex2)
                    {
                        Log.e(TAG, ex2.toString());
                        // NOP
                    }
                }
            }
            
            // doesn't match a wildcard, re-throw exception
            if (!ok)
            {                
                throw e;
            }
        }
    }
        
    /**
     * Gets a DefaultHttpClient that's tolerant of wildcard matches.
     * 
     * @param httpParams HTTP Parameters for a DefaultHttpClient
     * 
     * @return DefaultHttpClient that's tolerant of wildcard matches.
     */
    public static DefaultHttpClient getHttpsWildcardTolerantClient(final HttpParams httpParams)
    {
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        
        SSLSocketFactory sslSocketFactory =
            (SSLSocketFactory) client 
            .getConnectionManager()
            .getSchemeRegistry()
            .getScheme("https") 
            .getSocketFactory();
        
        final X509HostnameVerifier delegate = sslSocketFactory.getHostnameVerifier();
        
        if (!(delegate instanceof HttpsWildcardVerifier))
        { 
            sslSocketFactory.setHostnameVerifier(new HttpsWildcardVerifier(delegate)); 
        } 
        
        return client;          
    }
}
