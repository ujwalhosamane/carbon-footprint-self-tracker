package com.carbon.footprint.suggestion.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carbon.footprint.suggestion.model.SuggestionModel;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionModel, Long> {
	public List<SuggestionModel> findByUserId(String userId);  
	public List<SuggestionModel> findByCreationDate(LocalDate creationDate);
	public void deleteByUserId(String userId);
	public List<SuggestionModel> findByCreationDateAfter(LocalDate date);
	public List<SuggestionModel> findByUserIdAndCreationDateAfter(String userId, LocalDate date);
	
	@Query(
		    value = "SELECT * FROM suggestion_model s WHERE s.user_id = :userId ORDER BY s.creation_date DESC, s.suggestion_id DESC LIMIT 1", 
		    nativeQuery = true
		)
    Optional<SuggestionModel> findLatestSuggestionByUserId(String userId);
	
	@Transactional
    @Modifying
    @Query("DELETE FROM SuggestionModel s WHERE s.carbonFootprintId IN :carbonFootprintIds")
    void deleteAllByCarbonFootprintIdIn(List<Long> carbonFootprintIds);
	
	@Transactional
	void deleteByCarbonFootprintId(Long carbonFootprintId);
	
	List<SuggestionModel> findByCarbonFootprintId(Long carbonFootprintId);
}
