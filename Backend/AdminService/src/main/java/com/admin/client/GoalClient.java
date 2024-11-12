package com.admin.client;

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

import com.admin.dto.PredefinedGoal;
import com.admin.dto.PredefinedGoalDTO;

@FeignClient(name = "GOAL-SERVICE")
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
	
	
	@PostMapping("/goal/add/users/{predefinedId}")
	public ResponseEntity<Void> addGoal(@RequestBody List<String> users, @PathVariable Long predefinedId);
	
	@GetMapping("/goal/get/user/rewardPoints")
	public ResponseEntity<Map<String, Double>> getUserAndRewardPoints();
}
