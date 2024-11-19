package com.admin.client;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.dto.GlobalInsight;

@FeignClient(name = "GLOBAL-INSIGHTS-SERVICE")
public interface GlobalInsightsClient {
	@PostMapping(value="/globalInsight/addInsight/{userId}")
	public ResponseEntity<GlobalInsight> addInsight(@RequestBody GlobalInsight insight,
			@PathVariable String userId);
	
	@GetMapping("/globalInsight/allInsights")
	public ResponseEntity<List<GlobalInsight>> fetchAllInsight();
	
	@PutMapping(value="/globalInsight/updateInsight")
	public ResponseEntity<GlobalInsight> updateInsight(@RequestBody GlobalInsight insight);
	
	@GetMapping(value="/globalInsight/descriptionbydate/{date}")
	public ResponseEntity<List<GlobalInsight>> fetchDescriptionByDate(@PathVariable("date") LocalDate date);
	
	@DeleteMapping(value="/globalInsight/deleteInsight/{insightId}")
	public Map<String, String> removeInsightById(@PathVariable("insightId") long insightId);
	
}
