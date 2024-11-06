package com.carbon.footprint.suggestion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carbon.footprint.suggestion.model.SuggestionModel;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionModel, Long> {
	public List<SuggestionModel> findByUserId(long userId);  
	public List<SuggestionModel> findByCreationDate(LocalDate creationDate);
	public void deleteByUserId(long userId);
	public List<SuggestionModel> findByCreationDateAfter(LocalDate date);
	public List<SuggestionModel> findByUserIdAndCreationDateAfter(long userId, LocalDate date);
}
