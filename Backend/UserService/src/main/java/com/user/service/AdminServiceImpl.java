package com.user.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
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
				user.getEmail());
		
		return postCreationDTO;
	}

}
