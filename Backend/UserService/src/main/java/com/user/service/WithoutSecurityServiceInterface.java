package com.user.service;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserForgotPasswordDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
import com.user.exception.InvalidPasswordException;
import com.user.exception.UserNotFoundException;
import com.user.model.UserRole;

public interface WithoutSecurityServiceInterface {
	UserPostCreationDTO addUser(UserCreationDTO userCreationDTO) throws DuplicateUserException;
	UserPostCreationDTO updatePassword(UserForgotPasswordDTO userForgotPasswordDTO, UserRole role) 
			throws InvalidPasswordException, UserNotFoundException;
}
