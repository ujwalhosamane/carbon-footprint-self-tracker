package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.GlobalInsightsClient;

@RestController
@RequestMapping("/user")
public class UserInsightsController {
	@Autowired
	private GlobalInsightsClient globalInsightsClient;
	
	@GetMapping("/getNInsights/{n}")
	public ResponseEntity<List<String>> getNInsights(@PathVariable int n) {
		return globalInsightsClient.getNInsights(n);
	}
}
