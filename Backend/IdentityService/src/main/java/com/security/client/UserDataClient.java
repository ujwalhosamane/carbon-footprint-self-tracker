package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.dto.UserDataCreation;

@FeignClient(name = "USER-DATA-SERVICE")
public interface UserDataClient {
	@PostMapping("/user/add")
	public ResponseEntity<Void> addUser(@RequestBody UserDataCreation userDataCreation);
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId);
}
