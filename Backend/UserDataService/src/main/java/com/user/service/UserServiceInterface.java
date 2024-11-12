package com.user.service;

import java.util.List;
import java.util.Map;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserDataCreationDTO;

public interface UserServiceInterface {
	List<LeaderBoardOnFootprint> getBoardOnFootprints(String userId);
	List<LeaderBoardOnRewardPoints> getBoardOnRewardPoints(String userId);
	List<LeaderBoardOnSixMonthRewardPoints> getBoardOnSixMonthRewardPoints(String userId);
	
	// For Predefined Goal while creating new predefined goal
	List<String> getAllNonAdminUserId();
		
	void addUser(UserDataCreationDTO userDto);
	void deleteUserAccount(String userId);
	
	void updateTotalRewardPoints(Map<String, Double> userTotalRewardPoints);
}
