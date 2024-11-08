package com.user.service;

import java.util.List;
import java.util.Map;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.UserNotFoundException;

public interface UserServiceInterface {
	List<LeaderBoardOnFootprint> getBoardOnFootprints(String userId);
	List<LeaderBoardOnRewardPoints> getBoardOnRewardPoints(String userId);
	List<LeaderBoardOnSixMonthRewardPoints> getBoardOnSixMonthRewardPoints(String userId);
	
	UserPostCreationDTO updateUser(UserPostCreationDTO userPostCreationDTO, String userId) throws UserNotFoundException;
	Map<String, String> getUserIdByEmail(String email);
	void deleteUserAccount(String userId, String email);
	
}
