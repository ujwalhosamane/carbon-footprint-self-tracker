package com.carbon.footprint.service;

import java.util.List;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;

public interface CarbonFootprintServiceInterface {
	
	CarbonFootprint addFootprint(CarbonFootprintDTO footprintDto);
	List<CarbonFootprint> getFootprintsByUserId(String userId);
	CarbonFootprint updateFootprint(CarbonFootprintDTO footprintDto, Long footprintId);
	void deleteFootprint(Long footprintId);
	List<CarbonFootprint> getAllFootprint();
	
	// User
	CarbonFootprint findByUserIdAndMonthAndYear(String userId, String month, int year);
	CarbonFootprintDTO findAllSumsByUserId(String userId);
	List<CarbonFootprintDTO> findLastNmonthsSumsByUserId(String userId, int n);
	void deleteByUserId(String UserId);
	
	// Admin
	List<CarbonFootprintDTO> findByMonthAndYear(String month, int year);
	List<CarbonFootprintDTO> findLastNMonthsSums(int n);
	
}