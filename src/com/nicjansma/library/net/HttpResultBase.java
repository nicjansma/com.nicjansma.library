package com.nicjansma.library.net;

/**
 * HTTP results base class.
 */
public abstract class HttpResultBase
{
    //
    // Constants
    //
    /**
     * Successful HTTP status codes start.
     */
    private static final int HTTP_RESPONSE_SUCCESS_BLOCK_START = 200;

    /**
     * Successful HTTP status codes end.
     */
    private static final int HTTP_RESPONSE_SUCCESS_BLOCK_END = 299;

    //
    // Members
    //
    /**
     * The HTTP response.
     */
    private HttpSimpleResponse _httpResponse;

    //
    // Functions
    //
    /**
     * Sets the HTTP response.
     *
     * @param httpResponse The HTTP response.
     */
    public final void setHttpResponse(final HttpSimpleResponse httpResponse)
    {
        _httpResponse = httpResponse;
    }

    /**
     * Gets the HTTP response.
     *
     * @return The HTTP response.
     */
    public final HttpSimpleResponse getHttpResponse()
    {
        return _httpResponse;
    }

    /**
     * Gets the HTTP response code.
     *
     * @return The HTTP response code.
     */
    public final Boolean successful()
    {
        // stupid logic for now
        return _httpResponse != null
            && _httpResponse.getHttpCode() >= HTTP_RESPONSE_SUCCESS_BLOCK_START
            && _httpResponse.getHttpCode() < HTTP_RESPONSE_SUCCESS_BLOCK_END;
    }
}
