package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.GoalClient;
import com.user.dto.Goal;
import com.user.service.ClientService;
import com.user.util.JwtUtil;

@RestController
public class UserGoalController {
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/user/goal/get")
	public ResponseEntity<List<Goal>> getAllGoalDTO(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return goalClient.getAllGoalDTO(userId);
	}
	
	@GetMapping("/user/goal/total-count")
    public ResponseEntity<Map<String, Long>> getTotalCount(
    		@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return goalClient.getTotalCount(userId);
	}
	
	// updating the 6 months reward points
	@PutMapping("/get/and/update/rewardPoints/six")
	public ResponseEntity<Void> getAndUpdateSixMonthsRewardPoints() {
		Map<String, Double> userRewardPoints = goalClient.getUserAndSixMonthsRewardPoints().getBody();
		clientService.updateSixMonthRewardPoints(userRewardPoints);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/get/and/update/rewardPoints/")
	public ResponseEntity<Void> getAndUpdateRewardPoints() {
		Map<String, Double> userTotalRewardPoints = goalClient.getUserAndRewardPoints().getBody();
		clientService.updateTotalRewardPoints(userTotalRewardPoints);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
