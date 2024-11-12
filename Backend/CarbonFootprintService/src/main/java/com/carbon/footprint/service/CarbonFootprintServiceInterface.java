package com.carbon.footprint.service;

import java.time.LocalDate;
import java.util.List;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;

public interface CarbonFootprintServiceInterface {
	
	CarbonFootprintDTO addFootprint(String userId, CarbonFootprintDTO footprintDto, LocalDate accountCreationDate);
	List<CarbonFootprintDTO> getFootprintsByUserId(String userId);
	CarbonFootprintDTO updateFootprint(String userId, CarbonFootprintDTO footprintDto, LocalDate accountCreationDate);
	void deleteFootprint(String userId, String month, int year);
	List<CarbonFootprintDTO> getAllFootprint();
	
	// User
	CarbonFootprintDTO findByUserIdAndMonthAndYear(String userId, String month, int year);
	CarbonFootprintDTO findAllSumsByUserId(String userId);
	List<CarbonFootprintDTO> findLastNmonthsSumsByUserId(String userId, int n);
	void deleteByUserId(String UserId);
	
	// Admin
	List<CarbonFootprintDTO> findByMonthAndYear(String month, int year);
	List<CarbonFootprintDTO> findLastNMonthsSums(int n);
	
	// Goal
	CarbonFootprintDTO findHalfYearlySumsByYear(String userId, int year);
	
	// Goal validation
	List<String> getLast6MonthsFootprintCount(List<String> userIds);
}
