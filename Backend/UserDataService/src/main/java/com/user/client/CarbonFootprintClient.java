package com.user.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dto.CarbonFootprintDTO;
import com.user.dto.EmissionFactor;
import com.user.dto.LatestActivity;

@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface CarbonFootprintClient {
	/**
	 * USER
	 */
	@PostMapping("/carbonFootprint/add/{userId}")
	public ResponseEntity<CarbonFootprintDTO> addCarbonFootprint(
			@PathVariable("userId") String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	
	
	@PutMapping("/carbonFootprint/update/{userId}")
	public ResponseEntity<CarbonFootprintDTO> updateCarbonFootprint(
			@PathVariable("userId") String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	

	@DeleteMapping("/carbonFootprint/deleteAll/{userId}")
	public ResponseEntity<Void> deleteFootprintByUserId(@PathVariable("userId") String userId);
	
	@DeleteMapping("/carbonFootprint/delete/{month}/{year}/{userId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable("userId") String userId, 
			@PathVariable("month") String month, 
			@PathVariable("year") int year);
	
	@GetMapping("/carbonFootprint/get/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getFootprintsByUserId(@PathVariable("userId") String userId);
	
	@GetMapping("/carbonFootprint/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getByUserIdAndMonthAndYear(@PathVariable("userId") String userId, 
			@PathVariable("month") String month, 
			@PathVariable("year") int year);
	
	@GetMapping("/carbonFootprint/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(@PathVariable("userId") String userId, @PathVariable("months") int months);
	
	@GetMapping("/carbonFootprint/getAllUserSums/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getAllSumsByUserId(@PathVariable String userId);
	
	@GetMapping("/carbonFootprint/get/latest/{userId}")
    public ResponseEntity<CarbonFootprintDTO> getLatestCarbonFootprint(@PathVariable("userId") String userId);
	
	@GetMapping("/carbonFootprint/latest-activity")
    public ResponseEntity<LatestActivity> getLatestActivity(@RequestParam String userId);
	
	@GetMapping("/emission/factor")
    public ResponseEntity<EmissionFactor> getEmissionFactor();
	
	
}
