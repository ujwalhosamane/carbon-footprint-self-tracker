package com.goal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/get{userId}")
	public ResponseEntity<List<Goal>> getAllGoal(@PathVariable String userId) {
		return new ResponseEntity<>(goalService.getByUserId(userId), HttpStatus.OK);
	}
}
