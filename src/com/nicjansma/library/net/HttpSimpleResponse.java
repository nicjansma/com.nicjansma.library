package com.nicjansma.library.net;

/**
 * A simplified HTTP response.
 *
 * @author Nic Jansma
 */
public final class HttpSimpleResponse
{
    //
    // Constants
    //
    /**
     * Successful HTTP status code.
     */
    public static final int HTTP_RESPONSE_SUCCESS = 200;
    
    //
    // Members
    //
    /**
     * HTTP response string.
     */
    private String _response;

    /**
     * HTTP status code.
     */
    private int _httpCode;

    //
    // Functions
    //
    /**
     * HttpSimpleResponse constructor.
     *
     * @param response HTTP response string.
     * @param httpCode HTTP status code.
     */
    public HttpSimpleResponse(final String response, final int httpCode)
    {
        _response = response;
        _httpCode = httpCode;
    }

    /**
     * Gets the HTTP response.
     *
     * @return The HTTP response.
     */
    public String getResponse()
    {
        return _response;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The HTTP status code.
     */
    public int getHttpCode()
    {
        return _httpCode;
    }

    /**
     * Creates an error HTTP response.
     *
     * @return Error HTTP response
     */
    public static HttpSimpleResponse createErrorResponse()
    {
        return new HttpSimpleResponse("", 0);
    }
}
