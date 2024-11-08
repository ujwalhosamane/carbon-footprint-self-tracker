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

import com.user.dto.CarbonFootprint;
import com.user.dto.CarbonFootprintDTO;

@FeignClient(name = "CARBONFOOTPRINTSERVICE")
public interface CarbonFootprintClient {
	/**
	 * USER
	 */
	@PostMapping("/carbonFootprint/add")
	public ResponseEntity<CarbonFootprint> addCarbonFootprint(
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	
	
	@PutMapping("/update/{footprintId}")
	public ResponseEntity<CarbonFootprint> updateCarbonFootprint(
			@RequestBody CarbonFootprintDTO carbonFootprintDto, 
			@PathVariable Long footprintId,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate);
	

	@DeleteMapping("/deleteAll/{userId}")
	public ResponseEntity<Void> deleteFootprintByUserId(@PathVariable String userId);
	
	@DeleteMapping("/delete/{footprintId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable Long footprintId);
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<CarbonFootprint>> getFootprintsByUserId(@PathVariable String userId);
	
	@GetMapping("/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprint> getByUserIdAndMonthAndYear(@PathVariable String userId, 
			@PathVariable String month, 
			@PathVariable int year);
	
	@GetMapping("/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(@PathVariable String userId, @PathVariable int months);
	
	@GetMapping("/getAllUserSums/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getAllSumsByUserId(@PathVariable String userId);
	
	
	
	/**
	 * ADMIN
	 */
	@GetMapping("/getAll")
	public ResponseEntity<List<CarbonFootprint>> getAllFootprint();
	
	@GetMapping("/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year);
	
	@GetMapping("/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months);
}
