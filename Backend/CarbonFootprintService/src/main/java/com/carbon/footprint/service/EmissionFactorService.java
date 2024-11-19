package com.carbon.footprint.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbon.footprint.model.EmissionFactor;
import com.carbon.footprint.repository.EmissionFactorRepository;

@Service
public class EmissionFactorService {
	@Autowired
	private EmissionFactorRepository emissionFactorRepo;
	
	public EmissionFactor getEmissionFactor() {
        return emissionFactorRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No Emission Factor found in the database."));
    }
	
	public EmissionFactor updateEmissionFactor(EmissionFactor updatedEmissionFactor) {
        Optional<EmissionFactor> existingFactor = emissionFactorRepo.findById(updatedEmissionFactor.getEmissionFactorId());

        if (existingFactor.isPresent()) {
            EmissionFactor factor = existingFactor.get();
            factor.setTransportation(updatedEmissionFactor.getTransportation());
            factor.setElectricity(updatedEmissionFactor.getElectricity());
            factor.setLpg(updatedEmissionFactor.getLpg());
            factor.setShipping(updatedEmissionFactor.getShipping());
            factor.setAirConditioner(updatedEmissionFactor.getAirConditioner());
            return emissionFactorRepo.save(factor);
        } else {
            throw new RuntimeException("Emission Factor with ID " + updatedEmissionFactor.getEmissionFactorId() + " not found.");
        }
    }
	
	public void initializeDefaultEmissionFactor() {
        if (emissionFactorRepo.count() == 0) {
            EmissionFactor defaultEmissionFactor = new EmissionFactor(
                null,         
                0.14,         
                0.5,         
                3.0,         
                0.5,          
                0.4           
            );
            emissionFactorRepo.save(defaultEmissionFactor);
        }
    }
}
