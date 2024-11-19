package com.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.UserClient;

@RestController
@RequestMapping("/admin")
public class UserController {
	@Autowired
	private UserClient userClient;
	
	@GetMapping("/this-month/count")
    public ResponseEntity<Map<String, Long>> getThisMonthUserCount() {
		return new ResponseEntity<>(Map.of("count", userClient.getThisMonthUserCount()), HttpStatus.OK);
	}

    @GetMapping("/last-month/count")
    public ResponseEntity<Map<String, Long>> getLastMonthUserCount() {
    	return new ResponseEntity<>(Map.of("count", userClient.getLastMonthUserCount()), HttpStatus.OK);
    }
    
    @GetMapping("/total/count")
    public ResponseEntity<Map<String, Long>> getTotalUserCount() {
    	return new ResponseEntity<>(Map.of("count", userClient.getTotalUserCount()), HttpStatus.OK);
    }
    
    @GetMapping("/totals")
    public ResponseEntity<Map<String, Double>> getTotalFootprintAndRewardPoints() {
    	return new ResponseEntity<>(userClient.getTotalFootprintAndRewardPoints(), HttpStatus.OK);
    }
    
    @GetMapping("/get/topPerformer")
    public ResponseEntity<List<Map<String, Object>>> getTopPerformers() {
    	return new ResponseEntity<>(userClient.getTopPerformers(), HttpStatus.OK);
    }
}
