package com.user.service;

import java.util.List;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.exception.DuplicateUserException;

public interface AdminServiceInterface {
	UserPostCreationDTO addAdmin(UserCreationDTO userCreationDTO) throws DuplicateUserException;
}
