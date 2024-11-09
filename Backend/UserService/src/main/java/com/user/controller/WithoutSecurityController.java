package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserForgotPasswordDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.model.UserRole;
import com.user.service.WithoutSecurityServiceImpl;

@RestController
public class WithoutSecurityController {
	@Autowired
	private WithoutSecurityServiceImpl withoutSecurityService;
	
	@PostMapping("/userRegister")
	public ResponseEntity<UserPostCreationDTO> addUser(@RequestBody UserCreationDTO userCreationDTO) {
		return new ResponseEntity<>(withoutSecurityService.addUser(userCreationDTO), HttpStatus.OK);
	}
	
	@PutMapping("/userForgot")
	public ResponseEntity<UserPostCreationDTO> updateUserPassword(@RequestBody UserForgotPasswordDTO userForgotPasswordDTO) {
		return new ResponseEntity<>(withoutSecurityService.updatePassword(userForgotPasswordDTO, UserRole.USER), HttpStatus.OK);
	}
	
	@PutMapping("/adminForgot")
	public ResponseEntity<UserPostCreationDTO> updateAdminPassword(@RequestBody UserForgotPasswordDTO userForgotPasswordDTO) {
		return new ResponseEntity<>(withoutSecurityService.updatePassword(userForgotPasswordDTO, UserRole.ADMIN), HttpStatus.OK);
	}
}
