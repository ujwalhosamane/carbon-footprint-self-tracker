package com.user.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.stereotype.Service;
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
public class WithoutSecurityServiceImpl implements WithoutSecurityServiceInterface {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserPostCreationDTO addUser(UserCreationDTO userCreationDTO) 
			throws DuplicateUserException {
		User user = new User(
				userCreationDTO.getName(),
				userCreationDTO.getEmail(),
				userCreationDTO.getPassword(),
				userCreationDTO.getCity());
		user.setRole(UserRole.USER);
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
				user.getEmail());
		
		return postCreationDTO;
	}

	@Override
	public UserPostCreationDTO updatePassword(UserForgotPasswordDTO userForgotPasswordDTO, UserRole role)
			throws InvalidPasswordException, UserNotFoundException {
		
		User user = userRepository.findByEmail(userForgotPasswordDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found with this email"));
		
		if(!user.getRole().equals(role)) {
			throw new UserNotFoundException("User not found in " + role);
		}
		
		if(!user.getPassword().equals(userForgotPasswordDTO.getCurrentPassword())) {
			throw new InvalidPasswordException("Current password is not matching");
		}
		
		user.setPassword(userForgotPasswordDTO.getNewPassword());
		
		user = userRepository.save(user);
		
		UserPostCreationDTO postCreationDTO = new UserPostCreationDTO();
		postCreationDTO.setName(user.getName());
		postCreationDTO.setEmail(user.getEmail());
		
		return postCreationDTO;
	}

}
