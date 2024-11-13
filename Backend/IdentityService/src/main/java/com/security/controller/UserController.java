package com.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.client.UserDataClient;
import com.security.dto.AuthRequest;
import com.security.dto.UserCreationDTO;
import com.security.exception.DuplicateUserCreationException;
import com.security.service.AuthService;

import ch.qos.logback.core.subst.Token;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDataClient userDataClient;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreationDTO userCreationDTO) 
    		throws DuplicateUserCreationException {    		
    	authService.saveUser(userCreationDTO);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
    	Authentication authenticate = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
        				authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
        	String token = authService.generateToken(authRequest.getUsername());
        	String role = authService.extractRole(token);
        	Map<String, String> successfullLoginMap = Map.of(
        			"Token", token,
        			"Role", role);
        	return new ResponseEntity<>(successfullLoginMap, HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid access");
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserAccount(@RequestHeader("Authorization") String authorizationHeader) {
    	String token = authorizationHeader.substring(7); // "Bearer " is 7 characters
        String userId = authService.extractUserId(token);
        String role = authService.extractRole(token);
        
        authService.deleteByUserId(userId, role);
        userDataClient.deleteUser(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
