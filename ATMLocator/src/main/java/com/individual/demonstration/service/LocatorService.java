package com.individual.demonstration.service;

import com.individual.demonstration.api.AtmApi;
import com.individual.demonstration.error.InvalidAtmIdentifierException;
import com.individual.demonstration.error.ServiceProviderException;
import com.individual.demonstration.model.InlineResponse200;
import com.individual.demonstration.model.InlineResponse200ATM;
import com.individual.demonstration.model.InlineResponse200Data;
import com.individual.demonstration.model.MetaData;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Offers method to locate ATM based on identifier
 */
@Service
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class LocatorService {

    private final Logger logger = LoggerFactory.getLogger(LocatorService.class);

    private final AtmApi apiClient;

    @Autowired
    public LocatorService(final AtmApi apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * A method calls client method to load ATM data from endpoint service url. The client is
     * cacheable and hence there is no cache implemented at the entity level at the moment.
     *
     * @param atmIdentifier
     * @return
     */
    @Nullable
    public InlineResponse200ATM locate(@Nullable final String atmIdentifier){
        if(null == atmIdentifier || atmIdentifier.isBlank()){
            throw new InvalidAtmIdentifierException("Invalid ATM identifier");
        }
        final Map<String, InlineResponse200ATM> atmData = loadAtmData();
        return atmData.get(atmIdentifier);
    }

    private Map<String, InlineResponse200ATM> loadAtmData() {
        ResponseEntity<InlineResponse200> response = this.apiClient.atmsGet(null, null);
        if(HttpStatus.OK == response.getStatusCode()){
            final InlineResponse200 responseBody = response.getBody();
            if(null == responseBody){
                throw new ServiceProviderException("Empty response from service provider");
            }
            final MetaData metaData = responseBody.getMeta();
            final Integer totalResults = metaData.getTotalResults();
            if(null == totalResults){
                throw new ServiceProviderException("No entity found in response from service provider");
            }
            final List<InlineResponse200Data> data = responseBody.getData();
            if(null == data){
                throw new ServiceProviderException("Missing ATM data in response from service provider");
            }
            this.logger.info("ATM results retrieved from service provider(Cacheable): " + totalResults.intValue());
            return storeAtmDataFromData(data, totalResults.intValue());
        }
        throw new ServiceProviderException("Unknown response from service provider");
    }

    private static Map<String, InlineResponse200ATM> storeAtmDataFromData(final List<InlineResponse200Data> dataList, final int resultCount){
        final Map<String, InlineResponse200ATM> filteredAtmData = new HashMap<>(resultCount);
        dataList.forEach(data -> {
            data.getBrand().forEach(brand -> brand.getATM().forEach(atm -> filteredAtmData.put(atm.getIdentification(), atm)));
        });
        return filteredAtmData;
    }
}
