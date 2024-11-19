package com.admin.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.dto.CarbonFootprintDTO;
import com.admin.dto.EmissionFactor;

@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface CarbonFootprintClient {
	/**
	 * ADMIN
	 */
	@GetMapping("/carbonFootprint/getAll")
	public ResponseEntity<List<CarbonFootprintDTO>> getAllFootprint();
	
	@GetMapping("/carbonFootprint/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year);
	
	@GetMapping("/carbonFootprint/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months);
	
	/*
	 * Emission Factor
	 */
	@PutMapping("/emission/factor")
    public ResponseEntity<EmissionFactor> updateEmissionFactor(@RequestBody EmissionFactor updatedEmissionFactor);
	
	@GetMapping("/emission/factor")
    public ResponseEntity<EmissionFactor> getEmissionFactor();
	
	@GetMapping("/carbonFootprint/total")
    public Map<String, Double> getTotalFootprintForCurrentAndPreviousMonth();
	
	@GetMapping("/carbonFootprint/get/retention-date/{retentionDurationMonths}")
	public Date getRetentionDate(@PathVariable("retentionDurationMonths") int retentionDurationMonths);
}
