package com.adamdubiel.workshop.metrics.infrastructure;

import com.adamdubiel.workshop.metrics.config.HttpClientProperties;
import com.codahale.metrics.MetricRegistry;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class HttpClientFactory {

    private final MetricRegistry metricRegistry;

    private final HttpClientProperties config;

    public HttpClientFactory(
            MetricRegistry metricRegistry,
            HttpClientProperties httpClientProperties
    ) {
        this.metricRegistry = metricRegistry;
        this.config = httpClientProperties;
    }

    public HttpClient client() {
        return httpClient(connectionManager());
    }

    private HttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.getConnectionTimeout())
                .setSocketTimeout(config.getSocketTimeout())
                .build();

        HttpClientBuilder clientBuilder = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig);

        return clientBuilder.build();
    }

    PoolingHttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(config.getMaxConnections());
        manager.setDefaultMaxPerRoute(config.getMaxConnectionsPerRoute());

        // manager can be measured: manager.getTotalStats()

        return manager;
    }
}
