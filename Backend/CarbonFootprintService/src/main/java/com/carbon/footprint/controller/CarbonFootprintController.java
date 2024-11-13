package com.carbon.footprint.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;
import com.carbon.footprint.service.CarbonFootprintServiceImpl;

@RestController
@RequestMapping("/carbonFootprint")
@CrossOrigin
public class CarbonFootprintController {
	@Autowired
	private CarbonFootprintServiceImpl carbonFootprintService;
	
	//changed
	@PostMapping("/add/{userId}")
	public ResponseEntity<CarbonFootprintDTO> addCarbonFootprint(
			@PathVariable String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate) {
		return new ResponseEntity<>(carbonFootprintService.addFootprint(userId, carbonFootprintDto, accountCreationDate), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CarbonFootprintDTO>> getAllFootprint() {
		return new ResponseEntity<>(carbonFootprintService.getAllFootprint(), HttpStatus.OK);
	}
	
	//changed
	@PutMapping("/update/{userId}")
	public ResponseEntity<CarbonFootprintDTO> updateCarbonFootprint(
			@PathVariable String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate) {
		return new ResponseEntity<>(carbonFootprintService.updateFootprint(userId, carbonFootprintDto, accountCreationDate), HttpStatus.OK);
	}
	
	//changed
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getFootprintsByUserId(@PathVariable String userId) {
		return new ResponseEntity<>(carbonFootprintService.getFootprintsByUserId(userId), HttpStatus.OK);
	}
	
	//changed
	@DeleteMapping("/delete/{month}/{year}/{userId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable String userId, 
			@PathVariable String month, 
			@PathVariable int year) {
		carbonFootprintService.deleteFootprint(userId, month, year);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//changed
	@GetMapping("/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getByUserIdAndMonthAndYear(@PathVariable String userId, 
			@PathVariable String month, 
			@PathVariable int year) {
		return new ResponseEntity<>(carbonFootprintService.findByUserIdAndMonthAndYear(userId, month, year), HttpStatus.OK);
	}
	
	@GetMapping("/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year) {
		return new ResponseEntity<>(carbonFootprintService.findByMonthAndYear(month, year), HttpStatus.OK);
	}
	
	@GetMapping("/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months) {
		return new ResponseEntity<>(carbonFootprintService.findLastNMonthsSums(months), HttpStatus.OK);
	}
	
	@GetMapping("/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(@PathVariable String userId, @PathVariable int months) {
		return new ResponseEntity<>(carbonFootprintService.findLastNmonthsSumsByUserId(userId, months), HttpStatus.OK);
	}
	
	@GetMapping("/getAllUserSums/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getAllSumsByUserId(@PathVariable String userId) {
		return new ResponseEntity<>(carbonFootprintService.findAllSumsByUserId(userId),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAll/{userId}")
	public ResponseEntity<Void> deleteFootprintByUserId(@PathVariable String userId) {
		carbonFootprintService.deleteByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/toGoal/getSums/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getHalfYearSums(
			@PathVariable("userId") String userId, 
			@PathVariable("year") int year) {
		return new ResponseEntity<>(carbonFootprintService.findHalfYearlySumsByYear(userId, year), HttpStatus.OK);
	}
	
	@GetMapping("/count/forAllUserId")
	public ResponseEntity<List<String>> getCountOfFootprint(@RequestBody List<String> userIds) {
		return new ResponseEntity<List<String>>(carbonFootprintService.getLast6MonthsFootprintCount(userIds), HttpStatus.OK);
	}
}
