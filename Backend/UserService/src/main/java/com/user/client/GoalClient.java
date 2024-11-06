package com.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.dto.PredefinedGoal;
import com.user.dto.PredefinedGoalDTO;

@FeignClient(name = "GoalService")
public interface GoalClient {
	@PostMapping("/predefinedGoal/add")
	public ResponseEntity<PredefinedGoal> addPredefinedGoal(@RequestBody PredefinedGoalDTO predefinedGoalDto);
	
	@PostMapping("/goal/add/users/{predefinedId}")
	public ResponseEntity<Void> addGoal(@RequestBody List<String> users, @PathVariable Long predefinedId);
}
