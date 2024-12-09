package com.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.AdminAfterLogin;
import com.security.dto.UserCreationDTO;
import com.security.entity.UserRole;
import com.security.exception.DuplicateUserCreationException;
import com.security.service.AuthService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AuthService authService;
	
	@GetMapping("/latest-created")
    public List<Map<String, Object>> getLatestCreatedUsers() {
        return authService.getLatestCreatedUsers();
    }

    @GetMapping("/latest-logins")
    public List<Map<String, Object>> getLatestLoginActivities() {
        return authService.getLatestLoginActivities();
    }
    
    @GetMapping("/current-login-count")
    public Map<String, Long> getLoggedInNonAdminUserCount() {
        return authService.getLoggedInNonAdminUserCount();
    }
    
    @GetMapping("/get/admin-details")
    public ResponseEntity<AdminAfterLogin> getAdminAfterLogin(@RequestParam("token") String token) {
    	String role = authService.extractRole(token);
    	String userId = authService.extractUserId(token);
    	
    	if(!role.equals("ADMIN")) {
    		throw new RuntimeException("Not valid token");
    	}
    	
    	return new ResponseEntity<>(authService.getAdminAfterLogin(userId), HttpStatus.OK); 
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserCreationDTO userCreationDTO) 
            throws DuplicateUserCreationException {      
        authService.saveUser(userCreationDTO, UserRole.ADMIN);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
