package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}