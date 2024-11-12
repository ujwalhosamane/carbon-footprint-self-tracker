package com.goal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goal.client.CarbonFootprintClient;
import com.goal.dto.CarbonFootprintDTO;
import com.goal.service.GoalServiceImpl;

@RestController
@RequestMapping("/goal")
public class CarbonFootprintClientController {
	@Autowired
	private GoalServiceImpl goalService;
	
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@PutMapping("/update/score/{year}/{userId}")
	public ResponseEntity<Void> updateCurrentScoreByUserId(@PathVariable String userId,@PathVariable int year) {
		CarbonFootprintDTO carbonFootprintDto = footprintClient.getHalfYearSums(userId, year).getBody();
		goalService.updateScoreByUserId(carbonFootprintDto, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Scheduler
	@PutMapping("/update/achievement")
	public ResponseEntity<Void> updateAchievement() {
		goalService.updateAchievement(footprintClient.getCountOfFootprint(goalService.findAllDistinctUserIds()).getBody());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
