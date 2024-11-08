package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserCreationDTO;
import com.user.dto.UserPostCreationDTO;
import com.user.service.AdminServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminServiceImpl adminService;
	
	@PostMapping("/addAdmin")
	public ResponseEntity<UserPostCreationDTO> addAdmin(@RequestBody UserCreationDTO userCreationDTO) {
		return new ResponseEntity<>(adminService.addAdmin(userCreationDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}/{email}")
	public ResponseEntity<Void> deleteAdmin(
			@PathVariable String userId,
			@PathVariable String email) {
		adminService.deleteAdminAccount(userId, email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update/{userId}")
	ResponseEntity<UserPostCreationDTO> updateAdmin(
			@RequestBody UserPostCreationDTO userPostCreationDTO, 
			@PathVariable String userId) {
		return new ResponseEntity<>(adminService.updateAdmin(userPostCreationDTO, userId), HttpStatus.OK);
	}
}
