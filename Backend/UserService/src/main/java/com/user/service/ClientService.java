package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.repository.UserRepository;

@Service
public class ClientService implements ClientServiceInterface {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<String> getAllNonAdminUserId() {
		return userRepository.getAllNonAdminUserId();
	}
}
