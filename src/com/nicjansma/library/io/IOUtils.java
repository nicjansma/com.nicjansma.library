package com.nicjansma.library.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * IO utilities.
 * 
 * @author Nic Jansma
 */
public abstract class IOUtils
{

    /**
     * Converts an InputStream to a String.
     * 
     * @param is InputStream
     * 
     * @return String version of the InputStream
     */
    public static String convertStreamToString(final InputStream is)
    {
        // StringBuilder for the output 
        StringBuilder sb = new StringBuilder();

        // create a buffered reader from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try
        {
            // add to the output buffer one line at a time
            String line = null;
            
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return sb.toString();
    }
}
