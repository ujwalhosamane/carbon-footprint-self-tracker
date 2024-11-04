package com.carbon.footprint.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;
import com.carbon.footprint.service.CarbonFootprintServiceImpl;

@RestController
@RequestMapping("/carbonFootprint")
public class CarbonFootprintController {
	@Autowired
	private CarbonFootprintServiceImpl carbonFootprintService;
	
	@PostMapping("/add")
	public ResponseEntity<CarbonFootprint> addCarbonFootprint(@RequestBody CarbonFootprintDTO carbonFootprintDto) {
		return new ResponseEntity<>(carbonFootprintService.addFootprint(carbonFootprintDto), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CarbonFootprint>> getAllFootprint() {
		return new ResponseEntity<>(carbonFootprintService.getAllFootprint(), HttpStatus.OK);
	}
	
	@PutMapping("/update/{footprintId}")
	public ResponseEntity<CarbonFootprint> updateCarbonFootprint(@RequestBody CarbonFootprintDTO carbonFootprintDto, @PathVariable Long footprintId) {
		return new ResponseEntity<>(carbonFootprintService.updateFootprint(carbonFootprintDto, footprintId), HttpStatus.OK);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<CarbonFootprint>> getFootprintsByUserId(@PathVariable String userId) {
		return new ResponseEntity<>(carbonFootprintService.getFootprintsByUserId(userId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{footprintId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable Long footprintId) {
		carbonFootprintService.deleteFootprint(footprintId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprint> getByUserIdAndMonthAndYear(@PathVariable String userId, 
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
}