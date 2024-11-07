package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/leaderBoard/footprint/{userId}")
	public ResponseEntity<List<LeaderBoardOnFootprint>> getBoardOnFootprint(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getBoardOnFootprints(userId), HttpStatus.OK);
	}
	
	@GetMapping("/leaderBoard/rewardPoints/{userId}")
	public ResponseEntity<List<LeaderBoardOnRewardPoints>> getBoardOnRewardPoints(@PathVariable String userId) {
		return new ResponseEntity<>(userService.getBoardOnRewardPoints(userId), HttpStatus.OK);
	}
	
	
}
