package com.user.service;

import java.util.Map;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
import com.user.exception.UserNotFoundException;

public interface AdminServiceInterface {
	UserPostCreationDTO addAdmin(UserCreationDTO userCreationDTO) throws DuplicateUserException;
	UserPostCreationDTO updateAdmin(UserPostCreationDTO userPostCreationDTO, String userId) throws UserNotFoundException;
	void deleteAdminAccount(String userId, String email);
	Map<String, String> getUserIdByEmail(String email);
}
