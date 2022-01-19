package com.individual.demonstration.service;

import com.individual.demonstration.client.LocatorClient;
import com.individual.demonstration.error.InvalidAtmIdentifierException;
import com.individual.demonstration.model.InlineResponse200;
import com.individual.demonstration.model.InlineResponse200ATM;
import com.individual.demonstration.model.InlineResponse200Brand;
import com.individual.demonstration.model.InlineResponse200Data;
import com.individual.demonstration.model.Location;
import com.individual.demonstration.model.MetaData;
import com.individual.demonstration.model.PostalAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LocatorServiceTest {

    private static final String ATM_IDENTIFIER = "1";

    @Autowired
    LocatorService service;

    @MockBean
    LocatorClient client;

    @Test
    public void testLocateAtmIdNull(){
        Mockito.when(client.atmsGet(null, null)).thenReturn(mockResponse());
        final InvalidAtmIdentifierException exception = assertThrows(InvalidAtmIdentifierException.class, () -> {
              this.service.locate(null);
        });
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Invalid ATM identifier",exception.getMessage());
    }

    @Test
    public void testLocateAtmIdDoesNotMatch(){
        Mockito.when(client.atmsGet(null, null)).thenReturn(mockResponse());
        final InlineResponse200ATM response = this.service.locate("ATM_IDENTIFIER");
        Assertions.assertNull(response);
    }

    @Test
    public void testLocateAtmIdMatches(){
        Mockito.when(client.atmsGet(null, null)).thenReturn(mockResponse());
        final InlineResponse200ATM response = this.service.locate(ATM_IDENTIFIER);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ATM_IDENTIFIER, response.getIdentification());
    }

    private ResponseEntity<InlineResponse200> mockResponse() {
        return new ResponseEntity<>(new InlineResponse200()
                .addDataItem(getDataItem()) //
                .meta(new MetaData().totalResults(1)) //
                , HttpStatus.OK);
    }

    private InlineResponse200Data getDataItem() {
        return new InlineResponse200Data().addBrandItem(getBrandItem());
    }

    private InlineResponse200Brand getBrandItem() {
        return new InlineResponse200Brand().brandName("Lloyds") //
                .addATMItem(getAtmItem());
    }

    private InlineResponse200ATM getAtmItem() {
        return new InlineResponse200ATM()
                .identification(ATM_IDENTIFIER) //
                .supportedCurrencies(List.of("GBP")) //
                .location(getLocation());
    }

    private Location getLocation() {
        return new Location().postalAddress(
                new PostalAddress()
                        .addressLine(List.of("1 VICTORIA ROAD")) //
                        .streetName("17 ELLISON WALK") //
                        .townName("GATESHEAD") //
                        .countrySubDivision(List.of("TYNE AND WEAR")) //
                        .country("GB") //
                        .postCode("DH8 5AE"));
    }
}
