package com.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.CarbonFootprintClient;
import com.admin.dto.CarbonFootprintDTO;
import com.admin.dto.EmissionFactor;

@RestController
@RequestMapping("/admin/footprint")
public class FootprintController {
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CarbonFootprintDTO>> getAllFootprint() {
		return footprintClient.getAllFootprint();
	}
	
	@GetMapping("/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year) {
		return footprintClient.getSumByMonthAndYear(month, year);
	}
	
	@GetMapping("/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months) {
		return footprintClient.getNMonthsSum(months);
	}
	
	@PutMapping("/factor")
    public ResponseEntity<EmissionFactor> updateEmissionFactor(@RequestBody EmissionFactor updatedEmissionFactor){
		return footprintClient.updateEmissionFactor(updatedEmissionFactor);
	}
	
	@GetMapping("/factor")
    public ResponseEntity<EmissionFactor> getEmissionFactor() {
		return footprintClient.getEmissionFactor();
	}
	
	@GetMapping("/total")
    public ResponseEntity<Map<String, Double>> getTotalFootprintForCurrentAndPreviousMonth() {
		return new ResponseEntity<>(footprintClient.getTotalFootprintForCurrentAndPreviousMonth(), HttpStatus.OK);
	}
	
	@GetMapping("/get/retention-date/{retentionDurationMonths}")
	public ResponseEntity<Map<String, Date>> getRetentionDate(@PathVariable("retentionDurationMonths") int retentionDurationMonths) {
		return new ResponseEntity<>(Map.of("retentionDate", footprintClient.getRetentionDate(retentionDurationMonths)), HttpStatus.OK);
	}
}
