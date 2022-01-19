package com.individual.demonstration.controller;

import com.individual.demonstration.api.model.ATM;
import com.individual.demonstration.api.model.Address;
import com.individual.demonstration.error.EntityNotFoundException;
import com.individual.demonstration.model.InlineResponse200ATM;
import com.individual.demonstration.model.Location;
import com.individual.demonstration.model.PostalAddress;
import com.individual.demonstration.service.LocatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A handler for locator service
 */
@RestController
@RequestMapping(value = "/locator-service")
@NonNullByDefault({DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.FIELD})
public class LocatorController {

    private final Logger logger = LoggerFactory.getLogger(LocatorController.class);

    private final LocatorService service;

    public LocatorController(final LocatorService service) {
        this.service = service;
    }

    @Operation(summary = "ATM locator service which queries service provider master data and locate ATM by given identifier in request")
    @Parameter(description = "An identifier to be used to locate ATM information. Empty or invalid value fails the request")
    @GetMapping("/atms/{atmIdentifier}")
    public ResponseEntity<ATM> locateAtm(@PathVariable(required = false) final String atmIdentifier){
        this.logger.info("Request received with ATM identifier: " + atmIdentifier);
        final InlineResponse200ATM responseATM = this.service.locate(atmIdentifier);
        if(null == responseATM){
            throw new EntityNotFoundException("ATM not found with identifier: " + atmIdentifier);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(buildATM(responseATM));
    }

    private static ATM buildATM(final InlineResponse200ATM responseATM) {
        final Location location = responseATM.getLocation();
        final PostalAddress postalAddress = location.getPostalAddress();
        final Address address = new Address(postalAddress.getAddressLine().get(0), //
                postalAddress.getStreetName(), //
                postalAddress.getTownName(), //
                postalAddress.getCountrySubDivision().get(0), //
                postalAddress.getCountry(), //
                postalAddress.getPostCode());
        return new ATM(responseATM.getIdentification(), //
                    responseATM.getSupportedCurrencies(), //
                    address);
    }
}
