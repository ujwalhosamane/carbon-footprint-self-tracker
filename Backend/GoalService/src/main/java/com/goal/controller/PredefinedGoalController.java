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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goal.dto.PredefinedGoalDTO;
import com.goal.model.PredefinedGoal;
import com.goal.service.PredefinedGoalServiceImpl;

@RestController
@RequestMapping("/predefinedGoal")
public class PredefinedGoalController {
	@Autowired
	private PredefinedGoalServiceImpl predefinedGoalService;
	
	@PostMapping("/add")
	public ResponseEntity<PredefinedGoal> addPredefinedGoal(@RequestBody PredefinedGoalDTO predefinedGoalDto) {
		return new ResponseEntity<>(predefinedGoalService.savePredefinedGoal(predefinedGoalDto), HttpStatus.OK);
	}
	
	@GetMapping("/get/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> getPredefinedGoal(@PathVariable Long predefinedGoalId) {
		return new ResponseEntity<>(predefinedGoalService.getPredefinedGoal(predefinedGoalId), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<PredefinedGoal>> getAllPredefinedGoal() {
		return new ResponseEntity<>(predefinedGoalService.getAllPredefinedGoal(), HttpStatus.OK);
	}
	
	@PutMapping("/update/{predefinedGoalId}")
	public ResponseEntity<PredefinedGoal> updatePredefinedGoal(
			@RequestBody PredefinedGoalDTO predefinedGoalDto, 
			@PathVariable Long predefinedGoalId) {
		return new ResponseEntity<>(predefinedGoalService.updatePredefinedGoal(predefinedGoalDto, predefinedGoalId),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{predefinedGoalId}")
	public ResponseEntity<Void> deletePredefinedGoal(@PathVariable Long predefinedGoalId) {
		predefinedGoalService.deletePredefinedGoal(predefinedGoalId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/recent-goals/titles-descriptions")
    public List<Map<String, Object>> getRecentGoalTitlesAndDescriptions(@RequestParam(defaultValue = "2") int limit) {
        return predefinedGoalService.getRecentlyCreatedGoalTitlesAndDescriptions(limit);
    }
	
	@GetMapping("/with-goal-count")
    public List<Map<String, Object>> getPredefinedGoalsWithGoalCount(@RequestParam(defaultValue = "6") int limit) {
        return predefinedGoalService.getPredefinedGoalsWithGoalCount(limit);
    }
}
