package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.client.SuggestionClient;
import com.user.dto.SuggestionDTO;
import com.user.util.JwtUtil;

@RequestMapping("/user/suggestion")
@RestController
public class SuggestionController {
	
	@Autowired
	private SuggestionClient suggestionClient;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PutMapping("/update")
	public ResponseEntity<SuggestionDTO> updateSuggestion(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody SuggestionDTO suggestionDTO) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
        return suggestionClient.updateSuggestion(userId, suggestionDTO.getSuggestionId(), suggestionDTO);
	}
	
	@GetMapping("/regenerate")
	public ResponseEntity<Map<String, String>> regenerateSuggestion(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam("suggestionId") Long suggestionId) {
		
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
        return suggestionClient.regenerateSuggestion(userId, suggestionId);
	}
	
	@GetMapping("/latest")
	public ResponseEntity<SuggestionDTO> getLatestSuggestion(
			@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.substring(7); 
        String userId = jwtUtil.extractUserId(token);
        
        ResponseEntity<SuggestionDTO> responseEntity = suggestionClient.getLatestSuggestion(userId);

     if (responseEntity.getBody() == null) {
         return null;
     }

     SuggestionDTO suggestionDTO = responseEntity.getBody();
     System.out.println(suggestionDTO); 

     return responseEntity;

	}
}
