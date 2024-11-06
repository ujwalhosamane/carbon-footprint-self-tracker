package com.carbon.footprint.suggestion.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carbon.footprint.suggestion.DTO.SuggestionDTO;
import com.carbon.footprint.suggestion.model.SuggestionModel;
import com.carbon.footprint.suggestion.repository.SuggestionRepository;

@Service
public class SuggestionService {
	
	@Autowired
	private SuggestionRepository repository;
	
	public SuggestionModel createSuggestion(SuggestionModel suggestion)
	{
		return repository.save(suggestion);
	}
	
	public Optional<SuggestionModel> getSuggestionById(long suggestionId)
	{
		return repository.findById(suggestionId);
	}
	
	public List<SuggestionModel> getSuggestionByUserId(long userId)
	{
		return repository.findByUserId(userId);
	}
	
	public List<SuggestionModel> getAllSuggestions()
	{
		return repository.findAll();
	}
	
	public String getDescriptionById(long suggestionId)
	{
		return repository.findById(suggestionId)
				.map(SuggestionModel :: getDescription)
				.orElse(null);
	}
	
	public List<String> getDescriptionByDate(LocalDate date)
	{
		List<SuggestionModel> descriptions = repository.findByCreationDate(date);
		return descriptions
				.stream()
				.map(SuggestionModel :: getDescription)
				.collect(Collectors.toList());			
	}
	
/*	---------update service omitted
	public SuggestionModel updateSuggestion(SuggestionModel suggestion)
	{
		if(!repository.existsById(suggestion.getSuggestionId()))
		{
			throw new RuntimeException("the suggestion id doesn't exist ");
		}
		else
			return repository.save(suggestion);
	}--------  */
	
	public List<SuggestionDTO> getRecentSuggestions(int nDays) {
        LocalDate dateThreshold = LocalDate.now().minusDays(nDays);
        List<SuggestionModel> suggestions = repository.findByCreationDateAfter(dateThreshold);
        
        return suggestions.stream()
                          .map(suggestion -> new SuggestionDTO(suggestion.getDescription(), suggestion.getCreationDate()))
                          .collect(Collectors.toList());
    }
	public List<SuggestionDTO> getRecentSuggestionsByUserId(long userId, int nDays) {
        LocalDate dateThreshold = LocalDate.now().minusDays(nDays);
        List<SuggestionModel> suggestions = repository. findByUserIdAndCreationDateAfter(userId, dateThreshold);
        
        return suggestions.stream()
                          .map(suggestion -> new SuggestionDTO(suggestion.getDescription(), suggestion.getCreationDate()))
                          .collect(Collectors.toList());
    }
	public void deleteSuggestionbyId(long suggestionId)
	{
		repository.deleteById(suggestionId);
	}
	 @Transactional
	public void deleteSuggestionByUserId(long userId)
	{
		repository.deleteByUserId(userId);
	}

}
