package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;

class DefaultAsyncClientHttpRequestFactory implements AsyncClientHttpRequestFactory, ClientHttpRequestFactory {

    private final AsyncClientHttpRequestFactory asyncClientFactory;
    private final ClientHttpRequestFactory syncClientFactory;

    DefaultAsyncClientHttpRequestFactory(AsyncClientHttpRequestFactory asyncClientFactory,
                                         ClientHttpRequestFactory syncClientFactory) {
        this.asyncClientFactory = asyncClientFactory;
        this.syncClientFactory = syncClientFactory;
    }

    @Override
    public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return asyncClientFactory.createAsyncRequest(uri, httpMethod);
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return syncClientFactory.createRequest(uri, httpMethod);
    }
}
