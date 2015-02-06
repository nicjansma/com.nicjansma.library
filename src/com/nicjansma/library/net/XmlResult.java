package com.nicjansma.library.net;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * XML HTTP result.
 * 
 * @author Nic Jansma
 */ 
public final class XmlResult
    extends HttpResultBase
{
    //
    // Members
    //
    /**
     * The XML result (document).
     */
    private Document _doc = null;
    
    /**
     * Gets the XML result (in string form).
     * 
     * @return XML document in string form
     */
    public String getString()
    {
        return getHttpResponse() != null ? getHttpResponse().getResponse() : null;
    }
    
    /**
     * Gets the XML document of the result.
     * 
     * @return XML document, or null if it couldn't be parsed
     */
    public Document getDocument()
    {
        if (_doc != null)
        {
            return _doc;
        }
        
        if (getHttpResponse() == null
            || getHttpResponse().getResponse() == null
            || getHttpResponse().getResponse().length() == 0)
        {
            return null;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        _doc = null;
        
        try
        {
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(getHttpResponse().getResponse()));
            _doc = builder.parse(is);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        
        return _doc;
    }
}
