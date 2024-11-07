package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.GoalClient;
import com.user.dto.PredefinedGoal;
import com.user.dto.PredefinedGoalDTO;
import com.user.service.ClientService;

@RestController
@RequestMapping("/admin/predefinedGoal")
public class GoalClientController {
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/add")
	public ResponseEntity<PredefinedGoal> addPredefinedGoal(@RequestBody PredefinedGoalDTO predefinedGoalDTO) {
		ResponseEntity<PredefinedGoal> response = goalClient.addPredefinedGoal(predefinedGoalDTO);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			return response;
		}
		
		PredefinedGoal predefinedGoal = response.getBody();
		goalClient.addGoal(clientService.getAllNonAdminUserId(), predefinedGoal.getPredefinedGoalId());
		return new ResponseEntity<PredefinedGoal>(predefinedGoal, HttpStatus.OK);
	}
}
