package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.CarbonFootprintClient;
import com.user.client.GoalClient;
import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserAfterLogin;
import com.user.dto.UserDataCreationDTO;
import com.user.service.UserServiceImpl;
import com.user.util.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private GoalClient goalClient;
	
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/leaderBoard/footprint")
	public ResponseEntity<List<LeaderBoardOnFootprint>> getBoardOnFootprint(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		return new ResponseEntity<>(userService.getBoardOnFootprints(userId), HttpStatus.OK);
	}
	
	@GetMapping("/leaderBoard/rewardPoints")
	public ResponseEntity<List<LeaderBoardOnRewardPoints>> getBoardOnRewardPoints(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
		return new ResponseEntity<>(userService.getBoardOnRewardPoints(userId), HttpStatus.OK);
	}
	
	@GetMapping("/leaderBoard/rewardPoints/sixMonths")
	public ResponseEntity<List<LeaderBoardOnSixMonthRewardPoints>> getBoardOnSixMonthRewardPoints(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
		return new ResponseEntity<>(userService.getBoardOnSixMonthRewardPoints(userId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteUser(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
		userService.deleteUserAccount(userId);
		// Deleting all the goals related to the user (userId)
		goalClient.deleteByUserId(userId);
		footprintClient.deleteFootprintByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/getNonAdmins")
	public ResponseEntity<List<String>> getAllNonAdmin() {
		return new ResponseEntity<>(userService.getAllNonAdminUserId(), HttpStatus.OK);
	}
	
	@PutMapping("/update/totalFootprint")
	public ResponseEntity<Void> updateUserTotalFootprint(@RequestBody Map<String, Double> rewards) {
		userService.updateTotalRewardPoints(rewards);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Void> addUser(@RequestBody UserDataCreationDTO userCreationDTO) {
		userService.addUser(userCreationDTO);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/afterLogin/get")
	public ResponseEntity<UserAfterLogin> getAfterLogin(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
        return new ResponseEntity<>(userService.getUserAfterLogin(userId), HttpStatus.OK);
	}
	
}
