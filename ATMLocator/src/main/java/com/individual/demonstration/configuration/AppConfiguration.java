package com.individual.demonstration.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class AppConfiguration {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        final PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
        result.setMaxTotal(2);
        return result;
    }

    @Bean
    public CacheConfig cacheConfig() {
        return CacheConfig
                .custom()
                .setMaxObjectSize(500000) //
                .setMaxCacheEntries(2000) //
                .build();
    }

    @Bean
    public HttpClient httpClient(final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, final CacheConfig cacheConfig) {
        return CachingHttpClientBuilder
                .create()
                .setCacheConfig(cacheConfig)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(final HttpClient httpClient) {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }
}
