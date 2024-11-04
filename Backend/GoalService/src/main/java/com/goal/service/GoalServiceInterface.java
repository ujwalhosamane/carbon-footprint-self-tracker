package com.goal.service;

import java.util.List;

import com.goal.model.Goal;

public interface GoalServiceInterface {
	void saveGoal(String userId);
	List<Goal> getByUserId(String userId);
	void deleteByUserId(String UserId);
}
