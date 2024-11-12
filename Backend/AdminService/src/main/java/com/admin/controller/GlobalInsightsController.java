package com.admin.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.GlobalInsightsClient;
import com.admin.dto.GlobalInsight;

@RestController
@RequestMapping("/admin/globalInsight")
public class GlobalInsightsController {
	@Autowired
	private GlobalInsightsClient gloablInsightsClient;
	
	@PostMapping(value="/addInsight")
	public ResponseEntity<GlobalInsight> addInsight(@RequestBody GlobalInsight insight) {
		insight.setDate(LocalDate.now());
		return gloablInsightsClient.addInsight(insight);
	}
	
	@GetMapping("/allInsights")
	public ResponseEntity<List<GlobalInsight>> fetchAllInsight() {
		return gloablInsightsClient.fetchAllInsight();
	}
	
	@PutMapping(value="/updateInsight")
	public ResponseEntity<GlobalInsight> updateInsight(@RequestBody GlobalInsight insight) {
		return gloablInsightsClient.updateInsight(insight);
	}
	
	@GetMapping(value="/descriptionbydate/{date}")
	public ResponseEntity<List<GlobalInsight>> fetchDescriptionByDate(@PathVariable("date") LocalDate date) {
		return gloablInsightsClient.fetchDescriptionByDate(date);
	}
	
	@DeleteMapping(value="/deleteInsight/{insightId}")
	public String removeInsightById(@PathVariable("insightId") long insightId) {
		return gloablInsightsClient.removeInsightById(insightId);
	}
}
