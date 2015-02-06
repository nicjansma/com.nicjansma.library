package com.nicjansma.library.net;

/**
 * Simple HTTP fetcher.
 */
public interface IHttpSimpleFetcher
{
    /**
     * Fetch a URL.
     *
     * @param url URL to fetch
     *
     * @return HTTP response
     */
    HttpSimpleResponse fetch(final String url);
}
