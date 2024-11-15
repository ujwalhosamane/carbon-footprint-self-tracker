package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.client.UserDataClient;
import com.security.dto.UserCreationDTO;
import com.security.dto.UserDataCreation;
import com.security.entity.User;
import com.security.entity.UserRole;
import com.security.exception.DuplicateUserCreationException;
import com.security.exception.UserNotFoundException;
import com.security.respository.UserRepository;
import com.security.util.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDataClient userDataClient;
	
	public String saveUser(UserCreationDTO credential) throws DuplicateUserCreationException {
		if(userRepository.findByEmail(credential.getEmail()).isPresent()) {
			throw new DuplicateUserCreationException("User account already present with email " + credential.getEmail());
		}
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        User user = new User(
        		credential.getName(),
        		credential.getEmail(),
        		credential.getPassword(),
        		credential.getCity());
        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        
        UserDataCreation userDataCreation = new UserDataCreation(
        		user.getUserId(),
        		user.getName(),
        		user.getEmail(),
        		user.getCity(),
        		user.getCreationDate());
        if(!userDataClient.addUser(userDataCreation).getStatusCode().equals(HttpStatus.OK)) {
        	throw new RuntimeException("Error creating User data");
        }
        return "User added to the system";
    }

    public String generateToken(String email) {
    	Optional<User> optional = userRepository.findByEmail(email);
    	if(optional.isEmpty()) {
    		throw new UserNotFoundException("User not found with email " + email);
    	}
        return jwtUtil.generateToken(
        		optional.get().getUserId(), 
        		optional.get().getRole().toString());
    }

    public void validateToken(String token) {
    	jwtUtil.validateToken(token);
    }
    
    public String extractUserId(String token) {
    	return jwtUtil.extractUserId(token);
    }
    
    public String extractRole(String token) {
    	return jwtUtil.extractRole(token);
    }
    
    public void deleteByUserId(String userId, String role) {
    	if(userRepository.findById(userId).isEmpty()) {
    		throw new RuntimeException("Deletion of account failed as account not found");
    	}
    	
    	if(!userRepository.findById(userId).get().getRole().toString().equals(role)) {
    		throw new RuntimeException("Unauthorized access for deletion operation");
    	}
    	userRepository.deleteById(userId);
    }
}
