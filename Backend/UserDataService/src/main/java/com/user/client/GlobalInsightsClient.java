package com.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GLOBAL-INSIGHTS-SERVICE")
public interface GlobalInsightsClient {
	/*
	 * User
	 */
	@GetMapping("/globalInsight/getNInsights/{n}")
	public ResponseEntity<List<String>> getNInsights(@PathVariable int n);
}
