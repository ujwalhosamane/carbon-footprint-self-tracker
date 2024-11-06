package com.user.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.UserCreationDTO;
import com.user.dto.UserForgotPasswordDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
import com.user.exception.InvalidPasswordException;
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
				userRepository.getAllLeaderBoardOnFootprint(UserRole.USER, userId);
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		LeaderBoardOnFootprint userBoardOnFootprint = new LeaderBoardOnFootprint(
				user.getName(),
				user.getCity(),
				user.getTotalFootprint());
		userBoardOnFootprint.setCurrentUser(true);
		
		boardOnFootprints.add(userBoardOnFootprint);
		boardOnFootprints.sort(Comparator.comparing(LeaderBoardOnFootprint::getTotalFootprint));
		
		return boardOnFootprints;
	}

	@Override
	public List<LeaderBoardOnRewardPoints> getBoardOnRewardPoints(String userId) {
		List<LeaderBoardOnRewardPoints> boardOnRewardPoints = 
				userRepository.getAllLeaderBoardOnRewardPoints(UserRole.USER, userId);
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		LeaderBoardOnRewardPoints userBoardOnRewardPoints = new LeaderBoardOnRewardPoints(
				user.getName(),
				user.getCity(),
				user.getTotalRewardPoints());
		userBoardOnRewardPoints.setCurrentUser(true);
		
		boardOnRewardPoints.add(userBoardOnRewardPoints);
		boardOnRewardPoints.sort(Comparator.comparing(LeaderBoardOnRewardPoints::getTotalRewardPoints).reversed());
		
		return boardOnRewardPoints;
	}

//	@Override
//	public List<String> getAllNonAdminUserId() {	
//		return userRepository.getAllNonAdminUserId(UserRole.USER);
//	}
	
}
