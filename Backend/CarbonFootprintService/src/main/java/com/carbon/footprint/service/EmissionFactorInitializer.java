package com.carbon.footprint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmissionFactorInitializer implements CommandLineRunner {

    @Autowired
    private EmissionFactorService emissionFactorService;

    @Override
    public void run(String... args) throws Exception {
        emissionFactorService.initializeDefaultEmissionFactor();
    }
}
