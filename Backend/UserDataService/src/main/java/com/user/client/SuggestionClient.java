package com.user.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dto.SuggestionDTO;

@FeignClient(name = "SUGGESTION-SERVICE")
public interface SuggestionClient {
	
	@PutMapping("/suggestion/update/{userId}")
	public ResponseEntity<SuggestionDTO> updateSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("suggestionId") Long suggestionId,
			@RequestBody SuggestionDTO suggestionDTO);
	
	@GetMapping("/suggestion/regenerate/{userId}")
	public ResponseEntity<Map<String, String>> regenerateSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("suggestionId") Long suggestionId);
	
	@GetMapping("/suggestion/latest")
    public ResponseEntity<SuggestionDTO> getLatestSuggestion(@RequestParam("userId") String userId);
}
