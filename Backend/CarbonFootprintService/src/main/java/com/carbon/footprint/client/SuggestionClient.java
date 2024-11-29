package com.carbon.footprint.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.carbon.footprint.dto.CarbonFootprintDTO;

@FeignClient(name = "SUGGESTION-SERVICE")
public interface SuggestionClient {
	@PostMapping(value="/suggestion/addsuggestion/{userId}")
	public ResponseEntity<Void> addSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("carbonFootprintId") Long carbonFootprintId, 
			@RequestBody CarbonFootprintDTO carbonFootprintDTO);
	
	@DeleteMapping(value="/suggestion/delete/userid/{userId}")
	public String removeSuggestionByUserId(
			@PathVariable("userId") String userId);
	
	@DeleteMapping(value = "/suggestion/delete/footprintId/{footprintId}")
	public ResponseEntity<Void> deleteByFootprintId(
			@PathVariable("footprintId") Long footprintId);
	
	@DeleteMapping(value = "/suggestion/delete/footprintId")
	public ResponseEntity<Void> deleteAllByFootprintId(@RequestBody List<Long> footprintId);
	
	@PutMapping("/suggestion/regenerate/update/{userId}")
	public ResponseEntity<Void> regenerateAndUpdate(
			@PathVariable("userId") String userId,
			@RequestParam("carbonFootprintId") Long carbonFootprintId,
			@RequestBody CarbonFootprintDTO carbonFootprintDTO);
}
