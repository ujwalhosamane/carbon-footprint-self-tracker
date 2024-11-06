package com.user.service;

import java.util.List;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;

public interface UserServiceInterface {
	List<LeaderBoardOnFootprint> getBoardOnFootprints(String userId);
	List<LeaderBoardOnRewardPoints> getBoardOnRewardPoints(String userId);
//	List<String> getAllNonAdminUserId();
}
