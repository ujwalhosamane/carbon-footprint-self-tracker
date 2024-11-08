package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.CarbonFootprintClient;
import com.user.client.GoalClient;
import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserPostCreationDTO;
import com.user.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@GetMapping("/leaderBoard/footprint/{userId}")
	public ResponseEntity<List<LeaderBoardOnFootprint>> getBoardOnFootprint(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getBoardOnFootprints(userId), HttpStatus.OK);
	}
	
	@GetMapping("/leaderBoard/rewardPoints/{userId}")
	public ResponseEntity<List<LeaderBoardOnRewardPoints>> getBoardOnRewardPoints(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getBoardOnRewardPoints(userId), HttpStatus.OK);
	}
	
	@GetMapping("/leaderBoard/rewardPoints/sixMonths/{userId}")
	public ResponseEntity<List<LeaderBoardOnSixMonthRewardPoints>> getBoardOnSixMonthRewardPoints(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getBoardOnSixMonthRewardPoints(userId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}/{email}")
	public ResponseEntity<Void> deleteUser(
			@PathVariable String userId,
			@PathVariable String email) {
		userService.deleteUserAccount(userId, email);
		// Deleting all the goals related to the user (userId)
		goalClient.deleteByUserId(userId);
		footprintClient.deleteFootprintByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update/{userId}")
	ResponseEntity<UserPostCreationDTO> updateUser(
			@RequestBody UserPostCreationDTO userPostCreationDTO, 
			@PathVariable String userId) {
		return new ResponseEntity<>(userService.updateUser(userPostCreationDTO, userId), HttpStatus.OK);
	}
}
