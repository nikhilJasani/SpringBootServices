package com.individual.demonstration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocatorServiceTest {

    @Autowired
    LocatorService service;

    @Test
    public void testLocate(){
        final String response = this.service.locate("2");
        Assertions.assertNotNull(response);
    }
}