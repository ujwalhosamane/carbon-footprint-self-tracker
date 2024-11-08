package com.user.controller;

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

import com.user.client.GoalClient;
import com.user.dto.PredefinedGoal;
import com.user.dto.PredefinedGoalDTO;
import com.user.service.ClientService;

@RestController
@RequestMapping("/admin")
public class AdminGoalController {
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/predefinedGoal/add")
	public ResponseEntity<PredefinedGoal> addPredefinedGoal(@RequestBody PredefinedGoalDTO predefinedGoalDTO) {
		ResponseEntity<PredefinedGoal> response = goalClient.addPredefinedGoal(predefinedGoalDTO);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			return response;
		}
		
		PredefinedGoal predefinedGoal = response.getBody();
		goalClient.addGoal(clientService.getAllNonAdminUserId(), predefinedGoal.getPredefinedGoalId());
		return new ResponseEntity<PredefinedGoal>(predefinedGoal, HttpStatus.OK);
	}
	
	@GetMapping("/predefinedGoal/getAll")
	public ResponseEntity<List<PredefinedGoal>> getAllPredefinedGoal() {
		return goalClient.getAllPredefinedGoal();
	}
	
	@GetMapping("/predefinedGoal/get/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> getPredefinedGoal(@PathVariable Long predefinedGoalId) {
		return goalClient.getPredefinedGoal(predefinedGoalId);
	}
	
	@PutMapping("/predefinedGoal/update/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> updatePredefinedGoal(
			@RequestBody PredefinedGoalDTO predefinedGoalDto, 
			@PathVariable Long predefinedGoalId) {
		return goalClient.getPredefinedGoal(predefinedGoalId);
	}
	
	@DeleteMapping("/predefinedGoal/delete/{predefinedGoalId}")
	public ResponseEntity<Void> deletePredefinedGoal(@PathVariable Long predefinedGoalId) {
		ResponseEntity<Void> response = goalClient.deletePredefinedGoal(predefinedGoalId);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			Map<String, Double> userTotalRewardPoints = goalClient.getUserAndRewardPoints().getBody();
			clientService.updateTotalRewardPoints(userTotalRewardPoints);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return response;
	}
}
