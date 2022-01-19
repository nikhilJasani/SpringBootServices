package com.individual.demonstration.api.model;

import java.util.List;

/**
 * Represents information of ATM
 */
public class ATM {

    private final String identification;

    private final List<String> supportedCurrencies;

    private final Address address;

    public ATM(String identification, List<String> supportedCurrencies, Address address) {
        this.identification = identification;
        this.supportedCurrencies = supportedCurrencies;
        this.address = address;
    }

    public String getIdentification() {
        return identification;
    }

    public List<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public Address getAddress() {
        return address;
    }
}
