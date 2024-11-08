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
import org.springframework.web.bind.annotation.RequestBody;

import com.user.dto.Goal;
import com.user.dto.PredefinedGoal;
import com.user.dto.PredefinedGoalDTO;

@FeignClient(name = "GoalService")
public interface GoalClient {
	/**
	 * ADMIN
	 */
	@PostMapping("/predefinedGoal/add")
	public ResponseEntity<PredefinedGoal> addPredefinedGoal(@RequestBody PredefinedGoalDTO predefinedGoalDto);

	@GetMapping("/predefinedGoal/getAll")
	public ResponseEntity<List<PredefinedGoal>> getAllPredefinedGoal();

	@GetMapping("/predefinedGoal/get/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> getPredefinedGoal(@PathVariable Long predefinedGoalId);
	
	@PutMapping("/predefinedGoal/update/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> updatePredefinedGoal(
			@RequestBody PredefinedGoalDTO predefinedGoalDto, 
			@PathVariable Long predefinedGoalId);
	
	@DeleteMapping("/predefinedGoal/delete/{predefinedGoalId}")
	public ResponseEntity<Void> deletePredefinedGoal(@PathVariable Long predefinedGoalId);
	
	
	/**
	 * USER
	 */
	@PostMapping("/goal/add/users/{predefinedId}")
	public ResponseEntity<Void> addGoal(@RequestBody List<String> users, @PathVariable Long predefinedId);
	
	@DeleteMapping("/goal/delete/{userId}")
	public ResponseEntity<Void> deleteByUserId(@PathVariable String userId);
	
	@GetMapping("/goal/get/{userId}")
	public ResponseEntity<List<Goal>> getAllGoalDTO(@PathVariable String userId);
	
	// not added yet
	@PutMapping("/goal/update/score/{year}/{userId}")
	public ResponseEntity<Void> updateCurrentScoreByUserId(@PathVariable String userId, @PathVariable int year);
	
	@GetMapping("/goal/get/user/rewardPoints")
	public ResponseEntity<Map<String, Double>> getUserAndRewardPoints();
	
	@GetMapping("/goal/get/user/rewardPoints/six")
	public ResponseEntity<Map<String, Double>> getUserAndSixMonthsRewardPoints();
	
	
	/**
	 * Without Security
	 */
	@PostMapping("/goal/add/{userId}")
	public ResponseEntity<Void> addGoal(@PathVariable String userId);
}
