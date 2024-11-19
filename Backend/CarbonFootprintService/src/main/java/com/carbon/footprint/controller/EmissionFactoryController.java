package com.carbon.footprint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbon.footprint.model.EmissionFactor;
import com.carbon.footprint.service.EmissionFactorService;

@RestController
@RequestMapping("/emission/factor")
@CrossOrigin
public class EmissionFactoryController {
	@Autowired
	private EmissionFactorService emissionFactorService;
	
	@PutMapping
    public ResponseEntity<EmissionFactor> updateEmissionFactor(@RequestBody EmissionFactor updatedEmissionFactor) {
        EmissionFactor updatedFactor = emissionFactorService.updateEmissionFactor(updatedEmissionFactor);
        return ResponseEntity.ok(updatedFactor);
    }
	
	@GetMapping
    public ResponseEntity<EmissionFactor> getEmissionFactor() {
        EmissionFactor factor = emissionFactorService.getEmissionFactor();
        return ResponseEntity.ok(factor);
    }
	
}
