package com.individual.demonstration.service;

import com.individual.demonstration.api.AtmApi;
import com.individual.demonstration.model.InlineResponse200;
import com.individual.demonstration.model.InlineResponse200ATM;
import com.individual.demonstration.model.InlineResponse200Data;
import com.individual.demonstration.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.individual.demonstration.model.InlineResponse200Brand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocatorService {

    private Map<String, InlineResponse200ATM> atmData;

    private final AtmApi api;

    @Autowired
    public LocatorService(final AtmApi api) {
        this.api = api;
    }

    public String locate(final String atmIdentifier){
        ResponseEntity<InlineResponse200> response = this.api.atmsGet(null, null);
        if(HttpStatus.OK == response.getStatusCode()){
            final InlineResponse200 responseBody = response.getBody();
            if(null == responseBody){
                return null;
            }
            final MetaData metaData = responseBody.getMeta();
            final Integer totalResults = metaData.getTotalResults();
            if(null == totalResults){
                return null;
            }
            if(null == this.atmData){
                this.atmData = new HashMap<>(totalResults.intValue());
            }
            final List<InlineResponse200Data> data = responseBody.getData();
            if(null == data){
                return null;
            }
            storeAtmDataFromData(data);
        }
        return atmData.get(atmIdentifier).getLocation().getPostalAddress().toString();
    }

    private void storeAtmDataFromData(final List<InlineResponse200Data> dataList){
        dataList.forEach(data -> storeAtmDataFromBrand(data.getBrand()));
    }

    private void storeAtmDataFromBrand(final List<InlineResponse200Brand> brandList){
        brandList.forEach(brand -> storeAtmDataByIdentifier(brand.getATM()));
    }

    private void storeAtmDataByIdentifier(final List<InlineResponse200ATM> atmList){
        atmList.forEach(atm -> this.atmData.put(atm.getIdentification(), atm));
    }
}
