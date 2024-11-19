package com.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.UserIdenetityClient;
import com.admin.dto.AdminAfterLogin;
import com.admin.util.JwtUtil;

@RestController
@RequestMapping("/admin")
public class UserIdentityController {
	@Autowired
	private UserIdenetityClient userIdentityClient;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/latest-created")
    public ResponseEntity<List<Map<String, Object>>> getLatestCreatedUsers() {
		return new ResponseEntity<>(userIdentityClient.getLatestCreatedUsers(), HttpStatus.OK);
	}

    @GetMapping("/latest-logins")
    public ResponseEntity<List<Map<String, Object>>> getLatestLoginActivities() {
    	return new ResponseEntity<>(userIdentityClient.getLatestLoginActivities(), HttpStatus.OK);
    }
    
    @GetMapping("/current-login-count")
    public ResponseEntity<Map<String, Long>> getLoggedInNonAdminUserCount() {
    	return new ResponseEntity<>(userIdentityClient.getLoggedInNonAdminUserCount(), HttpStatus.OK);
    }
    
    @GetMapping("/get/admin-details")
    public ResponseEntity<AdminAfterLogin> getAdminAfterLogin(@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7);
        
        return userIdentityClient.getAdminAfterLogin(token);
    }
}
