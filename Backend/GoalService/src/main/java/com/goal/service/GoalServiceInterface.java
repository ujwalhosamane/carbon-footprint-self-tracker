package com.goal.service;

import java.util.List;
import java.util.Map;

import com.goal.dto.CarbonFootprintDTO;
import com.goal.dto.GoalDTO;
import com.goal.model.Goal;

public interface GoalServiceInterface {
	void saveGoal(String userId);
	void saveNewGoal(List<String> userId, Long predefinedGoalId);
	List<GoalDTO> getByUserId(String userId);
	void deleteByUserId(String UserId);
	
	void updateScoreByUserId(CarbonFootprintDTO carbonFootprintDto, String userId);
	
	//Scheduler
	void updateAchievement(List<String> users);
	void resetCurrentScore();
	
	List<String> findAllDistinctUserIds();
	
	Map<String, Double> getUserIdsWithTotalRewardPoints();
	Map<String, Double> getUserIdsWithSixMonthsRewardPoints();
}
