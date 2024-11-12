package com.admin.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-DATA-SERVICE")
public interface UserClient {
	@GetMapping("/user/getNonAdmins")
	public ResponseEntity<List<String>> getAllNonAdmin();
	
	@PutMapping("/user/update/totalFootprint")
	public ResponseEntity<Void> updateUserTotalFootprint(@RequestBody Map<String, Double> rewards);
}
