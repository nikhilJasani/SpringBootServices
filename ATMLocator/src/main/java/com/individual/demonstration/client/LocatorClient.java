package com.individual.demonstration.client;

import com.individual.demonstration.api.AtmApi;
import com.individual.demonstration.model.InlineResponse200;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * HTTPClient to send request to service provider
 */
@Component
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class LocatorClient implements AtmApi {

    private final Logger logger = LoggerFactory.getLogger(LocatorClient.class);

    private final RestTemplate restTemplate;

    private final String serviceUrl;

    @Autowired
    public LocatorClient(final RestTemplate restTemplate, @Value("${service.url:https://api.lloydsbank.com/open-banking/v2.2/atms}") final String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public ResponseEntity<InlineResponse200> atmsGet(String ifModifiedSince, String ifNoneMatch) {
        this.logger.info("Sending request to: " + this.serviceUrl);
        final ResponseEntity<InlineResponse200> response = this.restTemplate.getForEntity(this.serviceUrl, InlineResponse200.class);
        this.logger.info("Response received from: " + this.serviceUrl);
        return response;
    }
}
