package com.individual.demonstration.service;

import org.springframework.stereotype.Service;

@Service
public class LocatorService {

    public String locate(final String atmIdentifier){
        return "Located branch with id: " + atmIdentifier;
    }
}
