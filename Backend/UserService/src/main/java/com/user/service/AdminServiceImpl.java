package com.user.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
import com.user.exception.UserNotFoundException;
import com.user.model.User;
import com.user.model.UserRole;
import com.user.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminServiceInterface {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserPostCreationDTO addAdmin(UserCreationDTO userCreationDTO) throws DuplicateUserException {
		User user = new User(
				userCreationDTO.getName(),
				userCreationDTO.getEmail(),
				userCreationDTO.getPassword(),
				userCreationDTO.getCity());
		user.setRole(UserRole.ADMIN);
		user.setCreationDate(LocalDate.now());
		user.setTotalFootprint(Double.valueOf(0));
		user.setTotalRewardPoints(Double.valueOf(0));
		
		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			throw new DuplicateUserException("Email Id alredy exists");
		}
		
		UserPostCreationDTO postCreationDTO = new UserPostCreationDTO(
				user.getName(),
				user.getEmail(),
				user.getCity());
		
		return postCreationDTO;
	}
	
	@Override
	public Map<String, String> getUserIdByEmail(String email) {
		String userId = userRepository.getUserIdByEmail(email, UserRole.ADMIN)
				.orElseThrow(() -> new UserNotFoundException("User not found with the email " + email));
		
		return Map.of("userId", userId);
	}

	@Override
	public void deleteAdminAccount(String userId, String email) {
		try {
			userRepository.deleteByUserIdAndEmailAndRole(userId, email, UserRole.ADMIN);
		} catch (Exception e) {
			throw new UserNotFoundException("Unable to delete user with email " + email);
		}
	}

	@Override
	public UserPostCreationDTO updateAdmin(UserPostCreationDTO userPostCreationDTO, String userId)
			throws UserNotFoundException {
		User user = userRepository.findByUserIdAndEmail(userId, userPostCreationDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found with email " + userPostCreationDTO.getEmail()));
		
		if(!user.getRole().equals(UserRole.ADMIN)) {
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

}
