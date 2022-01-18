package com.individual.demonstration.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.individual.demonstration.api.AtmApi;
import com.individual.demonstration.model.InlineResponse200;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LocatorClient implements AtmApi {

    private final ObjectMapper objectMapper;

    @Autowired
    public LocatorClient(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<InlineResponse200> atmsGet(String ifModifiedSince, String ifNoneMatch) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        final HttpHeaders headers = new HttpHeaders();
        headers.setIfNoneMatch("78222-c3Z/Tn/yTf3OoxEwpZO2p8bAbB8");
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("https://api.lloydsbank.com/open-banking/v2.2/atms", HttpMethod.GET, entity, InlineResponse200.class);
    }
}
