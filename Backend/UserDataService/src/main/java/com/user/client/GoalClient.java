package com.user.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.user.dto.Goal;

@FeignClient(name = "GOAL-SERVICE")
public interface GoalClient {
	
	/**
	 * USER
	 */
	@DeleteMapping("/goal/delete/{userId}")
	public ResponseEntity<Void> deleteByUserId(@PathVariable String userId);
	
	@GetMapping("/goal/get/{userId}")
	public ResponseEntity<List<Goal>> getAllGoalDTO(@PathVariable String userId);
	
	// not added yet
	@PutMapping("/goal/update/score/{year}/{userId}")
	public ResponseEntity<Void> updateCurrentScoreByUserId(@PathVariable("userId") String userId, @PathVariable("year") int year);
	
	@GetMapping("/goal/get/user/rewardPoints")
	public ResponseEntity<Map<String, Double>> getUserAndRewardPoints();
	
	@GetMapping("/goal/get/user/rewardPoints/six")
	public ResponseEntity<Map<String, Double>> getUserAndSixMonthsRewardPoints();
	
	@GetMapping("/goal/total-count/{userId}")
    public ResponseEntity<Map<String, Long>> getTotalCount(@PathVariable String userId);
	
	
	/**
	 * Without Security
	 */
	@PostMapping("/goal/add/{userId}")
	public ResponseEntity<Void> addGoal(@PathVariable String userId);
}
