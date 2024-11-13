package com.user.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserAfterLogin;
import com.user.dto.UserDataCreationDTO;
import com.user.exception.UserNotFoundException;
import com.user.model.UserData;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<LeaderBoardOnFootprint> getBoardOnFootprints(String userId) {
		List<LeaderBoardOnFootprint> boardOnFootprints = 
				userRepository.getAllLeaderBoardOnFootprint(userId);
		
		UserData user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		LeaderBoardOnFootprint userBoardOnFootprint = new LeaderBoardOnFootprint(
				user.getName(),
				user.getCity(),
				user.getTotalFootprint());
		userBoardOnFootprint.setCurrentUser(true);
		
		boardOnFootprints.add(userBoardOnFootprint);
		boardOnFootprints.sort(Comparator.comparing(LeaderBoardOnFootprint::getTotalFootprint)
				.thenComparing(LeaderBoardOnFootprint::getName));
		
		return boardOnFootprints;
	}

	@Override
	public List<LeaderBoardOnRewardPoints> getBoardOnRewardPoints(String userId) {
		List<LeaderBoardOnRewardPoints> boardOnRewardPoints = 
				userRepository.getAllLeaderBoardOnRewardPoints(userId);
		
		UserData user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		LeaderBoardOnRewardPoints userBoardOnRewardPoints = new LeaderBoardOnRewardPoints(
				user.getName(),
				user.getCity(),
				user.getTotalRewardPoints());
		userBoardOnRewardPoints.setCurrentUser(true);
		
		boardOnRewardPoints.add(userBoardOnRewardPoints);
		boardOnRewardPoints.sort(
			    Comparator.comparing(LeaderBoardOnRewardPoints::getTotalRewardPoints)
			              .reversed()
			              .thenComparing(LeaderBoardOnRewardPoints::getName)
			);
		
		return boardOnRewardPoints;
	}

	@Override
	public void deleteUserAccount(String userId) {
		try {
			userRepository.deleteByUserId(userId);
		} catch (Exception e) {
			throw new UserNotFoundException("Unable to delete user");
		}
	}

	@Override
	public List<LeaderBoardOnSixMonthRewardPoints> getBoardOnSixMonthRewardPoints(String userId) {
		List<LeaderBoardOnSixMonthRewardPoints> boardOnSixMonthRewardPoints = 
				userRepository.getAllLeaderBoardOnSixMonthRewardPoints(userId);
		
		UserData user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		LeaderBoardOnSixMonthRewardPoints userBoardOnSixMonthRewardPoints = new LeaderBoardOnSixMonthRewardPoints(
				user.getName(),
				user.getCity(),
				user.getSixMonthRewardPoints());
		userBoardOnSixMonthRewardPoints.setCurrentUser(true);
		
		boardOnSixMonthRewardPoints.add(userBoardOnSixMonthRewardPoints);
		boardOnSixMonthRewardPoints.sort(
			    Comparator.comparing(LeaderBoardOnSixMonthRewardPoints::getSixMonthRewardPoints)
			              .reversed()
			              .thenComparing(LeaderBoardOnSixMonthRewardPoints::getName)
			);
		
		return boardOnSixMonthRewardPoints;
	}

	@Override
	public void addUser(UserDataCreationDTO userDto) {
		UserData user = new UserData(userDto.getUserId(),
				userDto.getName(),
				userDto.getEmail(),
				userDto.getCity(),
				userDto.getCreationDate());
		userRepository.save(user);
	}
	
	@Override
	public List<String> getAllNonAdminUserId() {
		return userRepository.getAllNonAdminUserId();
	}
	
	@Override
	public void updateTotalRewardPoints(Map<String, Double> userTotalRewardPoints) {
		for (Map.Entry<String, Double> entry : userTotalRewardPoints.entrySet()) {
            String userId = entry.getKey();
            Double totalRewardPoints = entry.getValue();
            userRepository.updateTotlaRewardPoints(userId, totalRewardPoints);
        }
	}

	@Override
	public UserAfterLogin getUserAfterLogin(String userId) {
		Optional<UserData> optional = userRepository.findByUserId(userId);
		if(optional.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		UserData userData = optional.get();
		UserAfterLogin afterLogin = new UserAfterLogin(
				userData.getName(),
				userData.getEmail(),
				userData.getCity(),
				userData.getCreationDate(),
				userData.getTotalFootprint(),
				userData.getSixMonthRewardPoints(),
				userData.getTotalRewardPoints());
		return afterLogin;
	}
	
	

}
