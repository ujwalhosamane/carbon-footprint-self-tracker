package com.user.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.UserNotFoundException;
import com.user.model.User;
import com.user.model.UserRole;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<LeaderBoardOnFootprint> getBoardOnFootprints(String userId) {
		List<LeaderBoardOnFootprint> boardOnFootprints = 
				userRepository.getAllLeaderBoardOnFootprint(userId);
		
		User user = userRepository.findById(userId)
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
		
		User user = userRepository.findById(userId)
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
	public void deleteUserAccount(String userId, String email) {
		try {
			userRepository.deleteByUserIdAndEmailAndRole(userId, email, UserRole.USER);
		} catch (Exception e) {
			throw new UserNotFoundException("Unable to delete user with email " + email);
		}
	}

	@Override
	public Map<String, String> getUserIdByEmail(String email) {
		String userId = userRepository.getUserIdByEmail(email, UserRole.USER)
				.orElseThrow(() -> new UserNotFoundException("User not found with the email " + email));
		
		return Map.of("userId", userId);
	}

	@Override
	public UserPostCreationDTO updateUser(UserPostCreationDTO userPostCreationDTO, String userId)
			throws UserNotFoundException {
		User user = userRepository.findByUserIdAndEmail(userId, userPostCreationDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found with email " + userPostCreationDTO.getEmail()));
		
		if(!user.getRole().equals(UserRole.USER)) {
			throw new UserNotFoundException("User not found");
		}
		
		user.setName(userPostCreationDTO.getName());
		user.setCity(userPostCreationDTO.getCity());
		
		try {
			user = userRepository.save(user);
		} catch(Exception ex) {
			throw new UserNotFoundException("User not found with email " + userPostCreationDTO.getEmail());
		}
		
		return userPostCreationDTO;
	}

	@Override
	public List<LeaderBoardOnSixMonthRewardPoints> getBoardOnSixMonthRewardPoints(String userId) {
		List<LeaderBoardOnSixMonthRewardPoints> boardOnSixMonthRewardPoints = 
				userRepository.getAllLeaderBoardOnSixMonthRewardPoints(userId);
		
		User user = userRepository.findById(userId)
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

}
