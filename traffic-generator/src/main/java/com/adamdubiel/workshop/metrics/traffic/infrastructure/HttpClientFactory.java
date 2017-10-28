package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;

@Component
public class HttpClientFactory {

    public AsyncRestTemplate create(int maxConns) {
        DefaultAsyncClientHttpRequestFactory requestFactory = asyncRequestFactory(maxConns);
        AsyncRestTemplate template = new AsyncRestTemplate(requestFactory, requestFactory);
        return template;
    }

    private DefaultAsyncClientHttpRequestFactory asyncRequestFactory(int maxConns) {
        HttpComponentsAsyncClientHttpRequestFactory clientFactory = asyncHttpRequestFactory(maxConns);
        return new DefaultAsyncClientHttpRequestFactory(
                clientFactory,
                clientFactory);
    }

    private HttpComponentsAsyncClientHttpRequestFactory asyncHttpRequestFactory(int maxConns) {
        return new HttpComponentsAsyncClientHttpRequestFactory(createAsyncClient(maxConns));
    }

    private CloseableHttpAsyncClient createAsyncClient(int maxConns) {
        HttpAsyncClientBuilder asyncClientBuilder = HttpAsyncClientBuilder.create()
                .setConnectionManager(connectionManager(maxConns));

        return asyncClientBuilder.build();
    }

    private PoolingNHttpClientConnectionManager connectionManager(int maxConns) {
        try {
            IOReactorConfig reactorConfig = IOReactorConfig.custom()
                    .setConnectTimeout(1000)
                    .setSoTimeout(1500)
                    .setSelectInterval(100)
                    .setTcpNoDelay(true)
                    .build();
            ConnectingIOReactor reactor = new DefaultConnectingIOReactor(
                    reactorConfig,
                    new ThreadFactoryBuilder().setNameFormat("http-client-%d").build()
            );

            PoolingNHttpClientConnectionManager manager;
            manager = new PoolingNHttpClientConnectionManager(reactor);

            manager.setMaxTotal(maxConns);
            manager.setDefaultMaxPerRoute(maxConns);

            return manager;
        } catch (IOReactorException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
