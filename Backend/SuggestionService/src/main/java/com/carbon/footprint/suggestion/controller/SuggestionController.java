package com.carbon.footprint.suggestion.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carbon.footprint.suggestion.DTO.SuggestionDTO;
import com.carbon.footprint.suggestion.model.SuggestionModel;
import com.carbon.footprint.suggestion.service.SuggestionService;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {
	
	@Autowired
	private SuggestionService service;
	
	@PostMapping(value="/addsuggestion")
	public ResponseEntity<SuggestionModel> addSuggestion(@RequestBody SuggestionModel suggestion)
	{
		SuggestionModel suggest = service.createSuggestion(suggestion);
		if(suggest != null)
			return ResponseEntity.ok(suggest);
		else
			return ResponseEntity.notFound().build();
		
	}
	 
	@GetMapping(value="/allsuggestions")
	public ResponseEntity<List<SuggestionModel>> fetchAllSuggestions()
	{
		List<SuggestionModel> suggestions = service.getAllSuggestions();
		return ResponseEntity.ok(suggestions);
	}
	
	@GetMapping(value="/suggestion/id/{suggestionId}")
	public ResponseEntity<Optional<SuggestionModel>> fetchSuggestionById(@PathVariable("suggestionId") long suggestionid)
	{
		Optional<SuggestionModel> suggestion = service.getSuggestionById(suggestionid);
		if(suggestion != null)
			return ResponseEntity.ok(suggestion);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value="/suggestion/userid/{userId}")
	public ResponseEntity<List<SuggestionModel>> fetchSuggestionsByUserId(@PathVariable("userId") long userId)
	{
		List<SuggestionModel> suggestions = service.getSuggestionByUserId(userId);
		if(!suggestions.isEmpty())
			return ResponseEntity.ok(suggestions);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value="/description/byDate/{date}")
	public ResponseEntity<List<String>> fetchDescriptionByDate(@PathVariable("date") LocalDate date)
	{
		List<String> descriptions = service.getDescriptionByDate(date);
		return ResponseEntity.ok(descriptions);
	}
	
	@GetMapping(value="/description/id/{suggestionId}")
	public ResponseEntity<String> fetchDescriptionById(@PathVariable("suggestionId") long suggestionId)
	{
		String description = service.getDescriptionById(suggestionId);
		return ResponseEntity.ok(description);
	}
	
	@GetMapping(value="/recent/totaldescription")
	public ResponseEntity<List<SuggestionDTO>> fetchRecentDescriptions(@RequestParam("days") int ndays)
	{
		List<SuggestionDTO> recents = service.getRecentSuggestions(ndays);
		return ResponseEntity.ok(recents);
	}
	@GetMapping("/recent/descriptionforuser")
    public ResponseEntity<List<SuggestionDTO>> fetchRecentSuggestionsforUser(
            @RequestParam long userId,
            @RequestParam int nDays) {
        List<SuggestionDTO> recentForUser = service.getRecentSuggestionsByUserId(userId, nDays);
        return ResponseEntity.ok(recentForUser);
    }
	
 /* ---- update suggestions omitted	
	@PutMapping(value="/update")
	public ResponseEntity<SuggestionModel> editSuggestion(@RequestBody SuggestionModel suggestion)
	{
		SuggestionModel updatedSuggestion = service.updateSuggestion(suggestion);
		return ResponseEntity.ok(updatedSuggestion);
	}------- */
	
	@DeleteMapping(value="/delete/userid/{userId}")
	public String removeSuggestionByUserId(@PathVariable("userId") long userId)
	{
		service.deleteSuggestionByUserId(userId);
		return "suggestions deleted since user has no access";
	}
 	
	@DeleteMapping(value="/delete/id/{suggestionId}")
	public String removeById(@PathVariable("suggestionId") long suggestionId)
	{
		service.deleteSuggestionbyId(suggestionId);
		return "suggestion deleted";
	}
}
