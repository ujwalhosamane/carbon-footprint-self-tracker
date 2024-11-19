package com.carbon.footprint.controller;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

import com.carbon.footprint.client.UserDataClient;
import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.dto.LatestActivity;
import com.carbon.footprint.service.CarbonFootprintServiceImpl;

@RestController
@RequestMapping("/carbonFootprint")
@CrossOrigin
public class CarbonFootprintController {
	@Autowired
	private CarbonFootprintServiceImpl carbonFootprintService;
	
	@Autowired
	private UserDataClient userDataClient;
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
			@PathVariable("userId") String userId,
			@RequestBody CarbonFootprintDTO carbonFootprintDto,
			@RequestParam("accountCreationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate accountCreationDate) {
		return new ResponseEntity<>(carbonFootprintService.updateFootprint(userId, carbonFootprintDto, accountCreationDate), HttpStatus.OK);
	}
	
	//changed
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getFootprintsByUserId(@PathVariable("userId") String userId) {
		return new ResponseEntity<>(carbonFootprintService.getFootprintsByUserId(userId), HttpStatus.OK);
	}
	
	//changed
	@DeleteMapping("/delete/{month}/{year}/{userId}")
	public ResponseEntity<Void> deleteFootprintById(@PathVariable("userId") String userId, 
			@PathVariable("month") String month, 
			@PathVariable("year") int year) {
		carbonFootprintService.deleteFootprint(userId, month, year);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//changed
	@GetMapping("/get/{month}/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getByUserIdAndMonthAndYear(@PathVariable("userId") String userId, 
			@PathVariable("month") String month, 
			@PathVariable("year") int year) {
		return new ResponseEntity<>(carbonFootprintService.findByUserIdAndMonthAndYear(userId, month, year), HttpStatus.OK);
	}
	
	@GetMapping("/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable("month") String month, 
			@PathVariable("year") int year) {
		return new ResponseEntity<>(carbonFootprintService.findByMonthAndYear(month, year), HttpStatus.OK);
	}
	
	@GetMapping("/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable("months") int months) {
		return new ResponseEntity<>(carbonFootprintService.findLastNMonthsSums(months), HttpStatus.OK);
	}
	
	@GetMapping("/getAllSums/{months}/{userId}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSumByUserId(
			@PathVariable("userId") String userId, 
			@PathVariable("months") int months) {
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
		Map<String, LocalDate> response = userDataClient.getUserIsWithCreatedAt(userIds);
		return new ResponseEntity<List<String>>(carbonFootprintService.getLast6MonthsFootprintCount(userIds, response), HttpStatus.OK);
	}
	
	@GetMapping("/get/latest/{userId}")
    public ResponseEntity<CarbonFootprintDTO> getLatestCarbonFootprint(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(carbonFootprintService.getLatestFootprint(userId), HttpStatus.OK);
    }
	
	@GetMapping("/latest-activity")
    public ResponseEntity<LatestActivity> getLatestActivity(@RequestParam String userId) {
        return new ResponseEntity<LatestActivity>( carbonFootprintService.getLatestActivity(userId), HttpStatus.OK);
    }
	
	@GetMapping("/total")
    public Map<String, Double> getTotalFootprintForCurrentAndPreviousMonth() {
        return carbonFootprintService.getTotalFootprintForCurrentAndPreviousMonth();
    }
	
	@DeleteMapping("/deleteOldFootprints/{retentionDurationMonths}")
    public ResponseEntity<String> deleteOldFootprints(@PathVariable int retentionDurationMonths) {
        carbonFootprintService.deleteOldFootprints(retentionDurationMonths);
        return ResponseEntity.ok("Carbon footprints older than " + retentionDurationMonths + " months have been deleted.");
    }
	
	@GetMapping("/get/retention-date/{retentionDurationMonths}")
	public Date getRetentionDate(@PathVariable("retentionDurationMonths") int retentionDurationMonths) {
		return carbonFootprintService.getRetentionDate(retentionDurationMonths);
	}
}
