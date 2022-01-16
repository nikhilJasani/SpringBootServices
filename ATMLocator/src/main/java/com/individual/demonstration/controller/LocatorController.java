package com.individual.demonstration.controller;

import com.individual.demonstration.service.LocatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocatorController {

    private final LocatorService service;

    public LocatorController(LocatorService service) {
        this.service = service;
    }

    @GetMapping("locator-service/atms/{atmIdentifier}")
    public String locate(@PathVariable(required = true) final String atmIdentifier){
        return this.service.locate(atmIdentifier);
    }
}
