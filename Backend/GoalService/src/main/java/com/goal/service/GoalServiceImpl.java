package com.goal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goal.model.Goal;
import com.goal.model.PredefinedGoal;
import com.goal.repository.GoalRepository;
import com.goal.repository.PredefinedGoalRepository;

import jakarta.transaction.Transactional;

@Service
public class GoalServiceImpl implements GoalServiceInterface {

	@Autowired
	private GoalRepository goalRepo;
	
	@Autowired
	private PredefinedGoalRepository predefinedGoalRepo;
	
	@Override
	public void saveGoal(String userId) {
		List<PredefinedGoal> predefinedGoal = predefinedGoalRepo.findAll();
		if(predefinedGoal.size() > 0) {
			for(PredefinedGoal predefined: predefinedGoal) {
				Goal userGoal = new Goal();
				userGoal.setCurrentScore(Long.valueOf(0));
				userGoal.setPredefinedGoal(predefined);
				userGoal.setUserId(userId);
				userGoal = goalRepo.save(userGoal);
				
				predefined.getGoals().add(userGoal);
				predefinedGoalRepo.save(predefined);
			}
		}
	}

	@Override
	@Transactional
	public void deleteByUserId(String userId) {
		goalRepo.deleteByUserId(userId);
	}

	@Override
	public List<Goal> getByUserId(String userId) {
		
		return goalRepo.findByUserId(userId);
	}
}