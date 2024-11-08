package com.user.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.exception.UserNotFoundException;
import com.user.model.User;
import com.user.repository.UserRepository;

@Service
public class ClientService implements ClientServiceInterface {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<String> getAllNonAdminUserId() {
		return userRepository.getAllNonAdminUserId();
	}
	

	@Override
	public LocalDate getUserCreationDateByUserId(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		return user.getCreationDate();
	}
	

	@Override
	public void updateUserTotalFootprint(String userId, Double totalFootprint) {
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isEmpty()) {
			throw new UserNotFoundException("User not found for the particular userId");
		}
		optional.get().setTotalFootprint(totalFootprint);
		userRepository.save(optional.get());
	}


	@Override
	public void updateSixMonthRewardPoints(Map<String, Double> userRewardPoints) {
		for (Map.Entry<String, Double> entry : userRewardPoints.entrySet()) {
            String userId = entry.getKey();
            Double rewardPoints = entry.getValue();
            userRepository.updateSixMonthRewardPoints(userId, rewardPoints);
        }
	}


	@Override
	public void updateTotalRewardPoints(Map<String, Double> userTotalRewardPoints) {
		for (Map.Entry<String, Double> entry : userTotalRewardPoints.entrySet()) {
            String userId = entry.getKey();
            Double totalRewardPoints = entry.getValue();
            userRepository.updateTotlaRewardPoints(userId, totalRewardPoints);
        }
	}

}
