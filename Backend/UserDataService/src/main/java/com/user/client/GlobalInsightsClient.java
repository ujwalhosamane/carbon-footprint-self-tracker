package com.user.client;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<Map<String, String>> getNInsights(@PathVariable int n);
}
