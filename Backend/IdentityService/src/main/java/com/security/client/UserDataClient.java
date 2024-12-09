package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.dto.UserDataCreation;

@FeignClient(name = "USER-DATA-SERVICE")
public interface UserDataClient {
	@PostMapping("/user/add")
	public ResponseEntity<Void> addUser(@RequestBody UserDataCreation userDataCreation);
	
	@DeleteMapping("/user/delete")
	public ResponseEntity<Void> deleteUser(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam("email") String email);
}
