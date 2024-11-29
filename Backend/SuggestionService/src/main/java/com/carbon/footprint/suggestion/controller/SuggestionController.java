package com.carbon.footprint.suggestion.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.hc.client5.http.ssl.HttpsSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.carbon.footprint.suggestion.DTO.CarbonFootprintDTO;
import com.carbon.footprint.suggestion.DTO.SuggestionDTO;
import com.carbon.footprint.suggestion.client.FootrpintClient;
import com.carbon.footprint.suggestion.client.GeminiClient;
import com.carbon.footprint.suggestion.model.SuggestionModel;
import com.carbon.footprint.suggestion.service.SuggestionService;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {
	
	@Autowired
	private SuggestionService service;
	
	@Autowired
	private GeminiClient geminiClient;
	
	@Autowired
	private FootrpintClient footrpintClient;
	
	@PostMapping(value="/addsuggestion/{userId}")
	public ResponseEntity<Void> addSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("carbonFootprintId") Long carbonFootprintId, 
			@RequestBody CarbonFootprintDTO carbonFootprintDTO)
	{
		String prompt = "Give suggestion to reduce the carbon footprint on the details of " + carbonFootprintDTO + ". Give it as single paragraph.";
		String suggestionString = geminiClient.generateTextWithSpring(prompt);
		
		SuggestionModel suggestionModel = new SuggestionModel(userId, suggestionString, carbonFootprintId);
		SuggestionModel suggest = service.createSuggestion(suggestionModel);
		if(suggest != null)
			return ResponseEntity.ok(null);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<SuggestionDTO> updateSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("suggestionId") Long suggestionId,
			@RequestBody SuggestionDTO suggestionDTO) {
		SuggestionModel suggestionModel = service.getSuggestionById(suggestionId).orElse(null);
		if(suggestionModel == null) {
			return ResponseEntity.notFound().build();
		}
		
		if(!suggestionModel.getUserId().equals(userId)) {
			return ResponseEntity.notFound().build();
		}
		
		suggestionModel.setDescription(suggestionDTO.getDescription());
		suggestionModel = service.createSuggestion(suggestionModel);
		
		return new ResponseEntity<>(new SuggestionDTO(
				suggestionModel.getSuggestionId(), 
				suggestionModel.getDescription(),
				suggestionModel.getCreationDate()), HttpStatus.OK);
	}
	
	@PutMapping("/regenerate/update/{userId}")
	public ResponseEntity<Void> regenerateAndUpdate(
			@PathVariable("userId") String userId,
			@RequestParam("carbonFootprintId") Long carbonFootprintId,
			@RequestBody CarbonFootprintDTO carbonFootprintDTO) {
		SuggestionModel suggestionModel = service.getByCarbonFootprintId(carbonFootprintId);
		if(suggestionModel == null) {
			return ResponseEntity.notFound().build();
		}

		if(!suggestionModel.getUserId().equals(userId)) {
			return ResponseEntity.notFound().build();
		}
		
		String prompt = "Give suggestion to reduce the carbon footprint on the details of " + carbonFootprintDTO + ". Give it as single paragraph.";
		String suggestionString = geminiClient.generateTextWithSpring(prompt);
		
		suggestionModel.setDescription(suggestionString);
		suggestionModel = service.createSuggestion(suggestionModel);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/regenerate/{userId}")
	public ResponseEntity<Map<String, String>> regenerateSuggestion(
			@PathVariable("userId") String userId,
			@RequestParam("suggestionId") Long suggestionId) {
		SuggestionModel suggestionModel = service.getSuggestionById(suggestionId).orElse(null);
		if(suggestionModel == null) {
			return ResponseEntity.notFound().build();
		}

		if(!suggestionModel.getUserId().equals(userId)) {
			return ResponseEntity.notFound().build();
		}
		
		CarbonFootprintDTO footprintDTO = footrpintClient.getDto(userId, suggestionModel.getCarbonFootprintId()).getBody();
		String prompt = "Give suggestion to reduce the carbon footprint on the details of " + footprintDTO + ". Give it as single paragraph.";
		String suggestionString = geminiClient.generateTextWithSpring(prompt);
		
		return new ResponseEntity<Map<String,String>>(Map.of("suggestion", suggestionString), HttpStatus.OK);
	}
	
	 
	@GetMapping(value="/allsuggestions")
	public ResponseEntity<List<SuggestionDTO>> fetchAllSuggestions()
	{
		List<SuggestionModel> suggestions = service.getAllSuggestions();
		if(suggestions.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		List<SuggestionDTO> suggestionDTOs = new ArrayList<>();
		for(SuggestionModel suggestionModel : suggestions) {
			SuggestionDTO suggestionDTO = new SuggestionDTO(
					suggestionModel.getSuggestionId(),
					suggestionModel.getDescription(),
					suggestionModel.getCreationDate());
			suggestionDTOs.add(suggestionDTO);
		}
		return ResponseEntity.ok(suggestionDTOs);
	}
	
	@GetMapping(value="/suggestion/id/{suggestionId}")
	public ResponseEntity<SuggestionDTO> fetchSuggestionById(@PathVariable("suggestionId") long suggestionid)
	{
		Optional<SuggestionModel> suggestion = service.getSuggestionById(suggestionid);
		if(suggestion != null){
			SuggestionModel suggestionModel = suggestion.get();
			SuggestionDTO suggestionDTO = new SuggestionDTO(
					suggestionModel.getSuggestionId(),
					suggestionModel.getDescription(),
					suggestionModel.getCreationDate());
		
			return new ResponseEntity<SuggestionDTO>(suggestionDTO, HttpStatus.OK);
		}
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value="/get/suggestion/{userId}")
	public ResponseEntity<List<SuggestionDTO>> fetchSuggestionsByUserId(@PathVariable("userId") String userId)
	{
		List<SuggestionModel> suggestions = service.getSuggestionByUserId(userId);
		if(!suggestions.isEmpty()){
			List<SuggestionDTO> suggestionDTOs = new ArrayList<>();
			for(SuggestionModel suggestionModel : suggestions) {
				SuggestionDTO suggestionDTO = new SuggestionDTO(
						suggestionModel.getSuggestionId(),
						suggestionModel.getDescription(),
						suggestionModel.getCreationDate());
				suggestionDTOs.add(suggestionDTO);
			}
			return ResponseEntity.ok(suggestionDTOs);
		}
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/latest")
    public ResponseEntity<SuggestionDTO> getLatestSuggestion(@RequestParam("userId") String userId) {
		Optional<SuggestionModel> optional = service.getLatestSuggestionByUserId(userId);
		if(optional.isEmpty()) {
			return ResponseEntity.ok(null);
		}
		
		SuggestionDTO suggestionDTO = new SuggestionDTO(
				optional.get().getSuggestionId(),
				optional.get().getDescription(),
				optional.get().getCreationDate());
        return new ResponseEntity<SuggestionDTO>(suggestionDTO, HttpStatus.OK);
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
            @RequestParam String userId,
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
	public String removeSuggestionByUserId(@PathVariable("userId") String userId)
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
	
	@DeleteMapping(value = "/delete/footprintId/{footprintId}")
	public ResponseEntity<Void> deleteByFootprintId(@PathVariable("footprintId") Long footprintId) {
		service.deleteByCarbonFootprintId(footprintId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/footprintId")
	public ResponseEntity<Void> deleteAllByFootprintId(@RequestBody List<Long> footprintId) {
		service.deleteAllByCarbonFootprintId(footprintId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/get/suggestion")
	public String getSuggestion() {
		String prompt = "Write a story about a magic backpack. Give it as single paragraph.";
		return geminiClient.generateTextWithSpring(prompt);
	}
}
