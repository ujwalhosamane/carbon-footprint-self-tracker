package com.user.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.CarbonFootprintClient;
import com.user.client.GoalClient;
import com.user.dto.CarbonFootprintDTO;
import com.user.exception.CarbonFootprintException;
import com.user.service.ClientService;
import com.user.util.JwtUtil;

@RestController
@RequestMapping("/user/carbonFootprint")
@CrossOrigin
public class UserFootprintCotroller {
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(UserFootprintCotroller.class);
	
	@PostMapping("/add")
	public ResponseEntity<CarbonFootprintDTO> addCarbonFootprint(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody CarbonFootprintDTO carbonFootprintDto){
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);

		if(footprintClient.getByUserIdAndMonthAndYear(
				userId,
				carbonFootprintDto.getFootprintMonth(), 
				carbonFootprintDto.getFootprintYear()).getBody() != null) {
			logger.info("Enterd first if condition");
			throw new CarbonFootprintException("Error occured while saving Footprint data");
		}
		ResponseEntity<CarbonFootprintDTO> response = footprintClient.
				addCarbonFootprint(userId, carbonFootprintDto, clientService.getUserCreationDateByUserId(userId));
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			LocalDate currentDate = LocalDate.now();
	        
			logger.info("Enterd second if condition");
			
	        int currentYear = currentDate.getYear();
			goalClient.updateCurrentScoreByUserId(userId, currentYear);
			
			ResponseEntity<CarbonFootprintDTO> dtoResponse = footprintClient.getAllSumsByUserId(userId);
			if(dtoResponse.getStatusCode().equals(HttpStatus.OK)) {
				logger.info("Enterd third if condition");
				CarbonFootprintDTO totalFootprintDTO = dtoResponse.getBody();
				Double totalFootprint = Double.valueOf(totalFootprintDTO.getTransportation()) + Double.valueOf(totalFootprintDTO.getElectricity()) +
						Double.valueOf(totalFootprintDTO.getLpg()) + Double.valueOf(totalFootprintDTO.getShipping()) + Double.valueOf(totalFootprintDTO.getAirConditioner());
				
				clientService.updateUserTotalFootprint(userId, totalFootprint);
			} else {
				throw new RuntimeException("Error occured while saving the footprint");
			}
		}
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}
	
	@PostMapping("/update")
	public ResponseEntity<CarbonFootprintDTO> updateCarbonFootprint(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody CarbonFootprintDTO carbonFootprint){
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);

		if(footprintClient.getByUserIdAndMonthAndYear(
				userId, 
				carbonFootprint.getFootprintMonth(), 
				carbonFootprint.getFootprintYear()) == null) {
			throw new CarbonFootprintException("Footprint data not found in the database");
		}
		CarbonFootprintDTO carbonFootprintDto = new CarbonFootprintDTO(
				carbonFootprint.getFootprintMonth(), 
				carbonFootprint.getFootprintYear(), 
				carbonFootprint.getTransportation(), 
				carbonFootprint.getElectricity(), 
				carbonFootprint.getLpg(), 
				carbonFootprint.getShipping(), 
				carbonFootprint.getAirConditioner());
		ResponseEntity<CarbonFootprintDTO> response = footprintClient.updateCarbonFootprint(
				userId,
				carbonFootprintDto, 
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
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}
	
	@DeleteMapping("/delete/{month}/{year}")
	public ResponseEntity<Void> deleteFootprintById(
			@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable String month, 
			@PathVariable int year) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return footprintClient.deleteFootprintById(userId, month, year);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<CarbonFootprintDTO>> getFootprintsByUserId(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return footprintClient.getFootprintsByUserId(userId);
	}
	
	@GetMapping("/getAllSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(
			@RequestHeader("Authorization") String authorizationHeader, 
			@PathVariable int months) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return footprintClient.getNMonthsSumByUserId(userId, months);
	}
	
}
