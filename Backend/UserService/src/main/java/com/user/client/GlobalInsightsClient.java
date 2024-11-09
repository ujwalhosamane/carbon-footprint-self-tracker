package com.user.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.dto.GlobalInsight;

@FeignClient(name = "GLOBALINSIGHTSSERVICE")
public interface GlobalInsightsClient {
	@PostMapping(value="/globalInsight/addInsight")
	public ResponseEntity<GlobalInsight> addInsight(@RequestBody GlobalInsight insight);
	
	@GetMapping("/globalInsight/allInsights")
	public ResponseEntity<List<GlobalInsight>> fetchAllInsight();
	
	@PutMapping(value="/globalInsight/updateInsight")
	public ResponseEntity<GlobalInsight> updateInsight(@RequestBody GlobalInsight insight);
	
	@GetMapping(value="/globalInsight/descriptionbydate/{date}")
	public ResponseEntity<List<GlobalInsight>> fetchDescriptionByDate(@PathVariable("date") LocalDate date);
	
	@DeleteMapping(value="/globalInsight/deleteInsight/{insightId}")
	public String removeInsightById(@PathVariable("insightId") long insightId);
	
	@DeleteMapping(value="/globalInsight/deletebyUser/{userId}")
	public String removeInsightByUserId(@PathVariable("userId") String userId);
	
	@GetMapping(value="/globalInsight/userInsights/{userId}")
	public ResponseEntity<List<GlobalInsight>> fetchInsightByUserId(@PathVariable("userId") String userId);
	
	
	/*
	 * User
	 */
	@GetMapping("/globalInsight/getNInsights/{n}")
	public ResponseEntity<List<String>> getNInsights(@PathVariable int n);
}
