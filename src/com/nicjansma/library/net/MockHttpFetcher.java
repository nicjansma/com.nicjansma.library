package com.nicjansma.library.net;

import java.util.ArrayList;

/**
 * A mock HTTP fetcher.
 */
public final class MockHttpFetcher
    implements IHttpSimpleFetcher
{
    /**
     * HTTP 200 OK status code.
     */
    private static final int HTTP_STATUS_200_OK = 200;

    /**
     * Array of responses.
     */
    private ArrayList<HttpSimpleResponse> _responses;

    /**
     * Empty Mock HTTP fetcher constructor.
     */
    public MockHttpFetcher()
    {
        // NOP
    }

    /**
     * Constructor.
     *
     * @param responses Responses to use.
     */
    public MockHttpFetcher(final ArrayList<HttpSimpleResponse> responses)
    {
        setResponses(responses);
    }

    /**
     * Sets the HTTP responses.
     *
     * @param responses HTTP responses
     */
    public void setResponses(final ArrayList<HttpSimpleResponse> responses)
    {
        _responses = responses;
    }

    /**
     * Clears all responses.
     */
    private void clearResponses()
    {
        _responses = new ArrayList<HttpSimpleResponse>();
    }

    /**
     * Sets a single HTTP 200 response.
     *
     * @param response HTTP response
     */
    public void setResponse(final String response)
    {
        clearResponses();
        addResponse(response, HTTP_STATUS_200_OK);
    }

    /**
     * Sets a single HTTP response.
     *
     * @param response HTTP response
     * @param httpCode HTTP code
     */
    public void setResponse(final String response, final int httpCode)
    {
        clearResponses();
        addResponse(response, httpCode);
    }

    /**
     * Adds a HTTP response.
     *
     * @param response HTTP response
     * @param httpCode HTTP code
     */
    public void addResponse(final String response, final int httpCode)
    {
        _responses.add(new HttpSimpleResponse(response, httpCode));
    }

    @Override
    public HttpSimpleResponse fetch(final String url)
    {
        if (_responses == null)
        {
            return HttpSimpleResponse.createErrorResponse();
        }

        HttpSimpleResponse currentResponse = _responses.get(0);

        // make room for the next response
        _responses.remove(0);

        return currentResponse;
    }

    /**
     * Determines if there are any responses left.
     *
     * @return True if there are responses left
     */
    public boolean hasResponsesLeft()
    {
        return _responses != null && _responses.size() > 0;
    }
}
