package com.user.service;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserForgotPasswordDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;
import com.user.exception.InvalidPasswordException;
import com.user.exception.UserNotFoundException;

public interface WithoutSecurityServiceInterface {
	UserPostCreationDTO addUser(UserCreationDTO userCreationDTO) throws DuplicateUserException;
	UserPostCreationDTO updatePassword(UserForgotPasswordDTO userForgotPasswordDTO) 
			throws InvalidPasswordException, UserNotFoundException;
}
