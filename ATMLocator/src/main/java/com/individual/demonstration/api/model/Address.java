package com.individual.demonstration.api.model;

/**
 * Represents information of ATM address
 */
public class Address {

    private final String addressLine;

    private final String streetName;

    private final String city;

    private final String countrySubDivision;

    private final String country;

    private final String postCode;

    public Address(String addressLine, String streetName, String city, String countrySubDivision, String country, String postCode) {
        this.addressLine = addressLine;
        this.streetName = streetName;
        this.city = city;
        this.countrySubDivision = countrySubDivision;
        this.country = country;
        this.postCode = postCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getCountrySubDivision() {
        return countrySubDivision;
    }

    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }
}
