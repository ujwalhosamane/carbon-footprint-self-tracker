package com.goal.service;

import java.util.List;

import com.goal.dto.PredefinedGoalDTO;
import com.goal.model.PredefinedGoal;

public interface PredefinedGoalServiceInterface {
	PredefinedGoal savePredefinedGoal(PredefinedGoalDTO predefinedGoalDto);
	PredefinedGoal getPredefinedGoal(Long predefinedGoalId);
	List<PredefinedGoal> getAllPredefinedGoal();
	PredefinedGoal updatePredefinedGoal(PredefinedGoalDTO predefinedGoalDto, Long predefinedGoalId);
	void deletePredefinedGoal(Long predefinedGoalId);
}
