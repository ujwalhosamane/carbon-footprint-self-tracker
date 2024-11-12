package com.user.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dto.CarbonFootprintDTO;

@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface CarbonFootprintClient {
	/**
	 * USER
	 */
	@PostMapping("/carbonFootprint/add/{userId}")
	public ResponseEntity<CarbonFootprintDTO> addCarbonFootprint(
			@PathVariable String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	
	
	@PutMapping("/carbonFootprint/update/{userId}")
	public ResponseEntity<CarbonFootprintDTO> updateCarbonFootprint(
			@PathVariable String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	

	@DeleteMapping("/carbonFootprint/deleteAll/{userId}")
	public ResponseEntity<Void> deleteFootprintByUserId(@PathVariable String userId);
	
	@DeleteMapping("/delete/{month}/{year}/{userId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable String userId, 
			@PathVariable String month, 
			@PathVariable int year);
	
	@GetMapping("/carbonFootprint/get/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getFootprintsByUserId(@PathVariable String userId);
	
	@GetMapping("/carbonFootprint/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getByUserIdAndMonthAndYear(@PathVariable String userId, 
			@PathVariable String month, 
			@PathVariable int year);
	
	@GetMapping("/carbonFootprint/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(@PathVariable String userId, @PathVariable int months);
	
	@GetMapping("/carbonFootprint/getAllUserSums/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getAllSumsByUserId(@PathVariable String userId);
	
}
