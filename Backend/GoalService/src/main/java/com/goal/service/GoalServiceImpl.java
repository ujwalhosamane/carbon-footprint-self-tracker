package com.goal.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goal.dto.CarbonFootprintDTO;
import com.goal.dto.GoalDTO;
import com.goal.exception.PredefinedGoalNotFoundException;
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
				userGoal.setCurrentScore(Double.valueOf(0));
				userGoal.setPredefinedGoal(predefined);
				userGoal.setUserId(userId);
				userGoal.setCount(Long.valueOf(0));
				userGoal.setAchieved(false);
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
	public List<GoalDTO> getByUserId(String userId) {
		return goalRepo.findDtoByUserId(userId);
	}

	@Override
	public void updateScoreByUserId(CarbonFootprintDTO carbonFootprintDto, String userId) {
		double totalFootprint = carbonFootprintDto.getTransportation() + carbonFootprintDto.getElectricity() +
				carbonFootprintDto.getLpg() + carbonFootprintDto.getShipping() + carbonFootprintDto.getAirConditioner();
		System.out.println("\n\nHere\n\n");
		List<Goal> userGoal = goalRepo.findByUserId(userId);
		for(Goal goal: userGoal) {
			PredefinedGoal predefinedGoal = goal.getPredefinedGoal();
//			if(goal.isAchieved()) continue;
			switch(predefinedGoal.getType()) {
				case "Transportation": {
					System.out.println(carbonFootprintDto.getTransportation());
						goal.setCurrentScore(Double.valueOf(carbonFootprintDto.getTransportation()));
						break;
				}
				
				case "Electricity": {
					System.out.println(carbonFootprintDto.getElectricity());
						goal.setCurrentScore(Double.valueOf(carbonFootprintDto.getElectricity()));
						break;
				}
				
				case "Lpg": {					System.out.println(carbonFootprintDto.getLpg());

						goal.setCurrentScore(Double.valueOf(carbonFootprintDto.getLpg()));
						break;
				}
				
				case "Shipping": {
						goal.setCurrentScore(Double.valueOf(carbonFootprintDto.getShipping()));
						break;
				}
				
				case "AirConditioner": {
						goal.setCurrentScore(Double.valueOf(carbonFootprintDto.getAirConditioner()));
						break;
				}
				
				case "Total": {
						goal.setCurrentScore(Double.valueOf(totalFootprint));
						break;
				}
			}
			
			goalRepo.save(goal);
		}
	}

	@Override
	public void updateAchievement(List<String> users) {
		List<Goal> userGoal = goalRepo.findAll();
		
		for(Goal goal: userGoal) {
			if(!users.contains(goal.getUserId())) continue;
			PredefinedGoal predefinedGoal = goal.getPredefinedGoal();
			if(predefinedGoal.getTargetScore() > goal.getCurrentScore()) {
				goal.setAchieved(true);
				goal.setCount(goal.getCount() + 1);
				goalRepo.save(goal);
			}
		}
	}

	@Override
	public void resetCurrentScore() {
		List<Goal> userGoal = goalRepo.findAll();
		for(Goal goal: userGoal) {
			goal.setAchieved(false);
			goal.setCurrentScore(Double.valueOf(0));
			goalRepo.save(goal);
		}
	}

	@Override
	public void saveNewGoal(List<String> userId, Long predefinedGoalId) {
		PredefinedGoal predefinedGoal = 
				predefinedGoalRepo.findByPredefinedGoalId(predefinedGoalId)
				.orElseThrow(() -> new PredefinedGoalNotFoundException("Predefined Goal not found"));
		
		for(String user: userId) {
			Goal userGoal = new Goal();
			userGoal.setCurrentScore(Double.valueOf(0));
			userGoal.setPredefinedGoal(predefinedGoal);
			userGoal.setUserId(user);
			userGoal.setCount(Long.valueOf(0));
			userGoal.setAchieved(false);
			userGoal = goalRepo.save(userGoal);
			
			predefinedGoal.getGoals().add(userGoal);
			predefinedGoalRepo.save(predefinedGoal);
		}
	}

	@Override
	public List<String> findAllDistinctUserIds() {
		return goalRepo.findAllDistinctUserIds();
	}
	
	@Override
	public Map<String, Double> getUserIdsWithTotalRewardPoints() {
        List<Object[]> results = goalRepo.findUserIdsWithTotalRewardPoints();
        
        return results.stream()
                      .collect(Collectors.toMap(
                          result -> (String) result[0],             
                          result -> ((Number) result[1]).doubleValue() 
                      ));
    }

	@Override
	public Map<String, Double> getUserIdsWithSixMonthsRewardPoints() {
		List<Object[]> results = goalRepo.findUserIdsWithSixMonthsRewardPoints();
		        
        return results.stream()
                      .collect(Collectors.toMap(
                          result -> (String) result[0],             
                          result -> ((Number) result[1]).doubleValue() 
                      ));
	}

}
