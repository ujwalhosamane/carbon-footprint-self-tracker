package com.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.client.UserDataClient;
import com.security.dto.AdminAfterLogin;
import com.security.dto.UserCreationDTO;
import com.security.dto.UserDataCreation;
import com.security.entity.LoginActivity;
import com.security.entity.User;
import com.security.entity.UserRole;
import com.security.exception.DuplicateUserCreationException;
import com.security.exception.UserNotFoundException;
import com.security.respository.LoginActivityRepository;
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
	
	@Autowired
    private LoginActivityRepository loginActivityRepository;

	public List<Map<String, Object>> getLatestCreatedUsers() {
	    Pageable pageable = PageRequest.of(0, 2);
	    List<User> users = userRepository.findLatestCreatedUsers(pageable);

	    return users.stream()
	        .map(user -> {
	            Map<String, Object> result = new HashMap<>();
	            result.put("Name", user.getName());
	            result.put("LastActivityTime", user.getCreationDate());
	            return result;
	        })
	        .collect(Collectors.toList());
	}

	public List<Map<String, Object>> getLatestLoginActivities() {
	    Pageable pageable = PageRequest.of(0, 2);
	    List<LoginActivity> activities = loginActivityRepository.findLatestLoginActivities(pageable);

	    return activities.stream()
	        .map(activity -> {
	            Map<String, Object> result = new HashMap<>();
	            result.put("Name", activity.getUser().getName());
	            result.put("LastActivityTime", activity.getActivityDate());
	            return result;
	        })
	        .collect(Collectors.toList());
	}
	
	public String saveUser(UserCreationDTO credential, UserRole role) throws DuplicateUserCreationException {
		if(userRepository.findByEmail(credential.getEmail()).isPresent()) {
			throw new DuplicateUserCreationException("User account already present with email " + credential.getEmail());
		}
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        User user = new User(
        		credential.getName(),
        		credential.getEmail(),
        		credential.getPassword(),
        		credential.getCity());
        user.setRole(role);
        user = userRepository.save(user);
        
        if(role.equals(UserRole.USER)) {
        	UserDataCreation userDataCreation = new UserDataCreation(
            		user.getUserId(),
            		user.getName(),
            		user.getEmail(),
            		user.getCity(),
            		user.getCreationDate());
            if(!userDataClient.addUser(userDataCreation).getStatusCode().equals(HttpStatus.OK)) {
            	userRepository.deleteById(user.getUserId());
            	throw new RuntimeException("Error creating User data");
            }
        }
        LoginActivity activity = new LoginActivity();
        activity.setUser(user);
        loginActivityRepository.save(activity);
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
    
    public void updatePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user); 
    }
    
    public void updateLoginStatus(String userId, boolean status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found "));
        if(status) {
        	user.setLoggedInSession(user.getLoggedInSession() + 1);
        	user.setLoggedIn(status);
        } else {
        	user.setLoggedInSession(user.getLoggedInSession() - 1);
        	if(user.getLoggedInSession() == 0) {
        		user.setLoggedIn(status);
        	}
        }
        
        userRepository.save(user);
    }
    
    public void recordActivity(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoginActivity activity = new LoginActivity();
        activity.setUser(user);

        user.getLoginActivities().add(activity);

        userRepository.save(user);
    }
    
    public Map<String, Long> getLoggedInNonAdminUserCount() {
        return Map.of("count", userRepository.countLoggedInNonAdminUsers());
    }
    
    public AdminAfterLogin getAdminAfterLogin(String userId) {
    	User user = userRepository.findById(userId).orElse(null);
    	if(user == null) {
    		throw new RuntimeException("user not found");
    	}
    	
    	AdminAfterLogin adminAfterLogin = new AdminAfterLogin(
    			user.getName(),
    			user.getEmail(),
    			user.getCity(),
    			user.getCreationDate().toLocalDate());
    	
    	return adminAfterLogin;
    }
}
