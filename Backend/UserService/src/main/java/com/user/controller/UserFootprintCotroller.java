package com.user.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.CarbonFootprintClient;
import com.user.client.GoalClient;
import com.user.dto.CarbonFootprint;
import com.user.dto.CarbonFootprintDTO;
import com.user.exception.CarbonFootprintException;
import com.user.service.ClientService;

@RestController
@RequestMapping("/user/carbonFootprint")
public class UserFootprintCotroller {
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private GoalClient goalClient;
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<CarbonFootprint> addCarbonFootprint(
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@PathVariable String userId){
		carbonFootprintDto.setUserId(userId);
		
		if(footprintClient.getByUserIdAndMonthAndYear(
				userId, 
				carbonFootprintDto.getFootprintMonth(), 
				carbonFootprintDto.getFootprintYear()) != null) {
			throw new CarbonFootprintException("Error occured while saving Footprint data");
		}
		ResponseEntity<CarbonFootprint> response = footprintClient.
				addCarbonFootprint(carbonFootprintDto, clientService.getUserCreationDateByUserId(userId));
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			LocalDate currentDate = LocalDate.now();
	        
	        int currentYear = currentDate.getYear();
			goalClient.updateCurrentScoreByUserId(userId, currentYear);
			
			ResponseEntity<CarbonFootprintDTO> dtoResponse = footprintClient.getAllSumsByUserId(userId);
			if(dtoResponse.getStatusCode().equals(HttpStatus.OK)) {
				CarbonFootprintDTO totalFootprintDTO = dtoResponse.getBody();
				Double totalFootprint = Double.valueOf(totalFootprintDTO.getTransportation()) + Double.valueOf(totalFootprintDTO.getElectricity()) +
						Double.valueOf(totalFootprintDTO.getLpg()) + Double.valueOf(totalFootprintDTO.getShipping()) + Double.valueOf(totalFootprintDTO.getAirConditioner());
				
				clientService.updateUserTotalFootprint(userId, totalFootprint);
			} else {
				throw new RuntimeException("Error occured while saving the footprint");
			}
		}
		return response;
	}
	
	@PostMapping("/update/{userId}")
	public ResponseEntity<CarbonFootprint> updateCarbonFootprint(
			@RequestBody CarbonFootprint carbonFootprint,
			@PathVariable String userId){
		if(footprintClient.getByUserIdAndMonthAndYear(
				userId, 
				carbonFootprint.getFootprintMonth(), 
				carbonFootprint.getFootprintYear()) == null) {
			throw new CarbonFootprintException("Footprint data not found in the database");
		}
		CarbonFootprintDTO carbonFootprintDto = new CarbonFootprintDTO(
				userId, 
				carbonFootprint.getFootprintMonth(), 
				carbonFootprint.getFootprintYear(), 
				carbonFootprint.getTransportation(), 
				carbonFootprint.getElectricity(), 
				carbonFootprint.getLpg(), 
				carbonFootprint.getShipping(), 
				carbonFootprint.getAirConditioner());
		ResponseEntity<CarbonFootprint> response = footprintClient.updateCarbonFootprint(
				carbonFootprintDto, 
				carbonFootprint.getCarbonFootprintId(), 
				clientService.getUserCreationDateByUserId(userId));
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			LocalDate currentDate = LocalDate.now();
	        
	        int currentYear = currentDate.getYear();
			goalClient.updateCurrentScoreByUserId(userId, currentYear);
			
			ResponseEntity<CarbonFootprintDTO> dtoResponse = footprintClient.getAllSumsByUserId(userId);
			if(dtoResponse.getStatusCode().equals(HttpStatus.OK)) {
				CarbonFootprintDTO totalFootprintDTO = dtoResponse.getBody();
				Double totalFootprint = Double.valueOf(totalFootprintDTO.getTransportation()) + Double.valueOf(totalFootprintDTO.getElectricity()) +
						Double.valueOf(totalFootprintDTO.getLpg()) + Double.valueOf(totalFootprintDTO.getShipping()) + Double.valueOf(totalFootprintDTO.getAirConditioner());
				
				clientService.updateUserTotalFootprint(userId, totalFootprint);
			} else {
				throw new RuntimeException("Error occured while saving the footprint");
			}
		}
		return response;
	}
	
	@DeleteMapping("/delete/{footprintId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable Long footprintId) {
		return footprintClient.deleteFootprintById(footprintId);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<CarbonFootprint>> getFootprintsByUserId(@PathVariable String userId) {
		return footprintClient.getFootprintsByUserId(userId);
	}
	
	@GetMapping("/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(@PathVariable String userId, @PathVariable int months) {
		return footprintClient.getNMonthsSumByUserId(userId, months);
	}
	
}
