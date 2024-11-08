package com.goal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goal.model.Goal;
import com.goal.service.GoalServiceImpl;

@RestController
@RequestMapping("/goal")
public class GoalController {
	@Autowired
	private GoalServiceImpl goalService;
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<Void> addGoal(@PathVariable String userId) {
		goalService.saveGoal(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Void> deleteByUserId(@PathVariable String userId) {
		goalService.deleteByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<Goal>> getAllGoal(@PathVariable String userId) {
		return new ResponseEntity<>(goalService.getByUserId(userId), HttpStatus.OK);
	}
	
	// Scheduler
	@PutMapping("/reset/score")
	public ResponseEntity<Void> resetCurrentScore() {
		goalService.resetCurrentScore();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// Admin
	@PostMapping("/add/users/{predefinedId}")
	public ResponseEntity<Void> addGoal(@RequestBody List<String> users, @PathVariable Long predefinedId) {
		goalService.saveNewGoal(users, predefinedId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// User
	@GetMapping("/get/user/rewardPoints")
	public ResponseEntity<Map<String, Double>> getUserAndRewardPoints() {
		return new ResponseEntity<Map<String,Double>>(goalService.getUserIdsWithTotalRewardPoints(), HttpStatus.OK);
	}
	
	// User
	@GetMapping("/get/user/rewardPoints/six")
	public ResponseEntity<Map<String, Double>> getUserAndSixMonthsRewardPoints() {
		return new ResponseEntity<Map<String,Double>>(goalService.getUserIdsWithSixMonthsRewardPoints(), HttpStatus.OK);
	}
	
}
