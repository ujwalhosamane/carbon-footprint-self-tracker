package com.goal.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goal.dto.PredefinedGoalDTO;
import com.goal.model.PredefinedGoal;
import com.goal.repository.GoalRepository;
import com.goal.repository.PredefinedGoalRepository;

@Service
public class PredefinedGoalServiceImpl implements PredefinedGoalServiceInterface {

	@Autowired
	private PredefinedGoalRepository predefinedGoalRepo;
	
	// Need to make changes
	@Autowired
	private GoalRepository goalRepo;
	
	@Override
	public PredefinedGoal savePredefinedGoal(PredefinedGoalDTO predefinedGoalDto) {
		PredefinedGoal predefinedGoal = new PredefinedGoal(
				predefinedGoalDto.getTitle(),
				predefinedGoalDto.getType(),
				predefinedGoalDto.getDescription(),
				predefinedGoalDto.getTargetScore(),
				predefinedGoalDto.getRewardPoint(),
				predefinedGoalDto.getBadgeUrl());
		predefinedGoal.setCreationDate(new Date());
		return predefinedGoalRepo.save(predefinedGoal);
	}

	@Override
	public PredefinedGoal getPredefinedGoal(Long predefinedGoalId) {
		return predefinedGoalRepo.findById(predefinedGoalId).orElse(null);
	}

	@Override
	public List<PredefinedGoal> getAllPredefinedGoal() {
		return predefinedGoalRepo.findAll();
	}

	@Override
	public PredefinedGoal updatePredefinedGoal(PredefinedGoalDTO predefinedGoalDto, Long predefinedGoalId) {
		PredefinedGoal predefinedGoal = predefinedGoalRepo.findByPredefinedGoalId(predefinedGoalId).orElse(null);
		if(predefinedGoal != null) {
			predefinedGoal.setTitle(predefinedGoalDto.getTitle());
			predefinedGoal.setDescription(predefinedGoalDto.getDescription());
			predefinedGoal.setTargetScore(predefinedGoalDto.getTargetScore());
			predefinedGoal.setRewardPoint(predefinedGoalDto.getRewardPoint());
			predefinedGoal.setCreationDate(new Date());
			predefinedGoal.setBadgeUrl(predefinedGoalDto.getBadgeUrl());
			predefinedGoal = predefinedGoalRepo.save(predefinedGoal);
		}
		return predefinedGoal;
	}

	@Override
	public void deletePredefinedGoal(Long predefinedGoalId) {
		predefinedGoalRepo.deleteById(predefinedGoalId);
	}

}
