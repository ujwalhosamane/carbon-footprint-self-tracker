package com.carbon.footprint.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.exception.FootprintDateException;
import com.carbon.footprint.model.CarbonFootprint;
import com.carbon.footprint.repository.CarbonFootprintRepository;

import jakarta.transaction.Transactional;

@Service
public class CarbonFootprintServiceImpl implements CarbonFootprintServiceInterface {

	@Autowired
	private CarbonFootprintRepository carbonFootprintRepository;
	
	@Override
	public CarbonFootprint addFootprint(CarbonFootprintDTO footprintDto, LocalDate accountCreationDate) {
		validateFootprintDate(footprintDto.getFootprintMonth(), footprintDto.getFootprintYear(), accountCreationDate);
		
		float totalFootprint = footprintDto.getTransportation() + footprintDto.getElectricity() +
				footprintDto.getLpg() + footprintDto.getShipping() + footprintDto.getAirConditioner();

		CarbonFootprint carbonFootprint = new CarbonFootprint();
		carbonFootprint.setUserId(footprintDto.getUserId());
		carbonFootprint.setFootprintMonth(footprintDto.getFootprintMonth());
		carbonFootprint.setFootprintYear(footprintDto.getFootprintYear());
		carbonFootprint.setTransportation(footprintDto.getTransportation());
		carbonFootprint.setElectricity(footprintDto.getElectricity());
		carbonFootprint.setLpg(footprintDto.getLpg());
		carbonFootprint.setShipping(footprintDto.getShipping());
		carbonFootprint.setAirConditioner(footprintDto.getAirConditioner());
		
		carbonFootprint.setTotalFootprint(totalFootprint);
		carbonFootprint.setCreationDate(LocalDate.now());
		
		return carbonFootprintRepository.save(carbonFootprint);
	}

	@Override
	public List<CarbonFootprint> getFootprintsByUserId(String userId) {
		List<CarbonFootprint> listCarbonFootprint = carbonFootprintRepository.findByUserId(userId);
		
		return listCarbonFootprint.size() > 0 ?  listCarbonFootprint : null;
	}

	@Override
	public void deleteFootprint(Long footprintId) {
		carbonFootprintRepository.deleteById(footprintId);
	}

	@Override
	public List<CarbonFootprint> getAllFootprint() {
		
		return carbonFootprintRepository.findAll();
	}

	@Override
	public CarbonFootprint updateFootprint(CarbonFootprintDTO footprintDto, Long footprintId, LocalDate accountCreationDate) {
		CarbonFootprint carbonFootprint = carbonFootprintRepository.findByCarbonFootprintId(footprintId);
		
		if(!carbonFootprint.getUserId().equals(footprintDto.getUserId())) {
			carbonFootprint = null;
		}
		
		if(carbonFootprint != null) {
			validateFootprintDate(footprintDto.getFootprintMonth(), footprintDto.getFootprintYear(), accountCreationDate);
			
			float totalFootprint = footprintDto.getTransportation() + footprintDto.getElectricity() +
					footprintDto.getLpg() + footprintDto.getShipping() + footprintDto.getAirConditioner();
			
			carbonFootprint.setFootprintMonth(footprintDto.getFootprintMonth());
			carbonFootprint.setFootprintYear(footprintDto.getFootprintYear());
			carbonFootprint.setTransportation(footprintDto.getTransportation());
			carbonFootprint.setElectricity(footprintDto.getElectricity());
			carbonFootprint.setLpg(footprintDto.getLpg());
			carbonFootprint.setShipping(footprintDto.getShipping());
			carbonFootprint.setAirConditioner(footprintDto.getAirConditioner());
			
			carbonFootprint.setTotalFootprint(totalFootprint);
			carbonFootprint.setCreationDate(LocalDate.now());
		}
		
		return carbonFootprint != null ?
				carbonFootprintRepository.save(carbonFootprint) :
					null;
	}

	@Override
	public CarbonFootprint findByUserIdAndMonthAndYear(String userId, String month, int year) {
		Optional<CarbonFootprint> footprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(userId, month, year);
		return footprint.isPresent() ?
				footprint.get() :
					null;
	}
	
	@Override
	public List<CarbonFootprintDTO> findByMonthAndYear(String month, int year) {
		List<CarbonFootprint> carbonFootprint = carbonFootprintRepository.findAllSumsByMonthAndYear(month, year).orElse(null);
		List<CarbonFootprintDTO> carbonFootprintDto = new ArrayList<>();
		if(carbonFootprint.size() > 0) {
			for(CarbonFootprint footprint: carbonFootprint) {
				CarbonFootprintDTO footprintDto = 
						new CarbonFootprintDTO(footprint.getTransportation(),
								footprint.getElectricity(),
								footprint.getLpg(),
								footprint.getShipping(),
								footprint.getAirConditioner());
				footprintDto.setFootprintMonth(month);
				footprintDto.setFootprintYear(year);
				carbonFootprintDto.add(footprintDto);
			}
		}
		
		return carbonFootprintDto;
    }

	@Override
	public List<CarbonFootprintDTO> findLastNMonthsSums(int n) {
        List<CarbonFootprintDTO> nMonthsResult = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < n; i++) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            String month = getMonthName(calendar.get(Calendar.MONTH));
            int year = calendar.get(Calendar.YEAR);
            
            List<CarbonFootprint> carbonFootprint = carbonFootprintRepository.findSumsByMonthAndYear(month, year).orElse(null);
            
            if(carbonFootprint.size() > 0) {
            	float totalTransportation = 0;
    			float totalElectricity = 0;
    			float totalLpg = 0;
    			float totalShipping = 0;
    			float totalAirConditioner = 0;
    			for(CarbonFootprint footprint: carbonFootprint) {
    				totalTransportation += footprint.getTransportation();
    				totalElectricity += footprint.getElectricity();
    				totalLpg += footprint.getLpg();
    				totalShipping += footprint.getShipping();
    				totalAirConditioner += footprint.getAirConditioner();
    			}
    			
    			CarbonFootprintDTO carbonFootprintDto = new CarbonFootprintDTO(totalTransportation, 
    					totalElectricity,
    					totalLpg,
    					totalShipping,
    					totalAirConditioner);
    			carbonFootprintDto.setFootprintMonth(month);
    			carbonFootprintDto.setFootprintYear(year);
    			
    			nMonthsResult.add(carbonFootprintDto);
            }
        }

        return nMonthsResult;
    }
	
	private void validateFootprintDate(String footprintMonth, int footprintYear, LocalDate creationDate) {
	    int creationYear = creationDate.getYear();
	    Month creationMonth = creationDate.getMonth();

	    LocalDate currentDate = LocalDate.now();
	    int currentYear = currentDate.getYear();
	    Month currentMonth = currentDate.getMonth();

	    Month footprintMonthEnum = Month.valueOf(footprintMonth.toUpperCase());

	    if (footprintYear < creationYear || 
	        (footprintYear == creationYear && footprintMonthEnum.compareTo(creationMonth) < 0)) {
	        throw new FootprintDateException("Footprint month and year cannot be earlier than the account creation date.");
	    }

	    if (footprintYear > currentYear || 
	        (footprintYear == currentYear && footprintMonthEnum.compareTo(currentMonth) > 0)) {
	        throw new FootprintDateException("Footprint month and year cannot be later than the current date.");
	    }
	}

//	private int getMonthIndex(String monthName) {
//	    switch (monthName) {
//	        case "January": return Calendar.JANUARY;
//	        case "February": return Calendar.FEBRUARY;
//	        case "March": return Calendar.MARCH;
//	        case "April": return Calendar.APRIL;
//	        case "May": return Calendar.MAY;
//	        case "June": return Calendar.JUNE;
//	        case "July": return Calendar.JULY;
//	        case "August": return Calendar.AUGUST;
//	        case "September": return Calendar.SEPTEMBER;
//	        case "October": return Calendar.OCTOBER;
//	        case "November": return Calendar.NOVEMBER;
//	        case "December": return Calendar.DECEMBER;
//	        default: throw new IllegalArgumentException("Invalid month name: " + monthName);
//	    }
//	}
	

    private String getMonthName(int monthIndex) {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return months[monthIndex];
    }

	@Override
	public List<CarbonFootprintDTO> findLastNmonthsSumsByUserId(String userId, int n) {
		List<CarbonFootprintDTO> nMonthsResult = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < n; i++) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            String month = getMonthName(calendar.get(Calendar.MONTH));
            int year = calendar.get(Calendar.YEAR);
            
            CarbonFootprint carbonFootprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(userId, month, year).orElse(null);
            if(carbonFootprint != null) {
            	CarbonFootprintDTO eachMonth = new CarbonFootprintDTO(
            			carbonFootprint.getTransportation(),
						carbonFootprint.getElectricity(),
						carbonFootprint.getLpg(),
						carbonFootprint.getShipping(),
						carbonFootprint.getAirConditioner());
            	eachMonth.setFootprintMonth(month);
            	eachMonth.setFootprintYear(year);
            	eachMonth.setUserId(userId);
                nMonthsResult.add(eachMonth);
            } else {
            	nMonthsResult.add(null);
            }
        }

        return nMonthsResult;
	}

	@Override
	public CarbonFootprintDTO findAllSumsByUserId(String userId) {
		List<CarbonFootprint> carbonFootprint = carbonFootprintRepository.findByUserId(userId);
		CarbonFootprintDTO carbonFootprintDto = null;
		if(carbonFootprint.size() > 0) {
			float totalTransportation = 0;
			float totalElectricity = 0;
			float totalLpg = 0;
			float totalShipping = 0;
			float totalAirConditioner = 0;
			for(CarbonFootprint footprint: carbonFootprint) {
				totalTransportation += footprint.getTransportation();
				totalElectricity += footprint.getElectricity();
				totalLpg += footprint.getLpg();
				totalShipping += footprint.getShipping();
				totalAirConditioner += footprint.getAirConditioner();
			}
			
			carbonFootprintDto = new CarbonFootprintDTO(totalTransportation, 
					totalElectricity,
					totalLpg,
					totalShipping,
					totalAirConditioner);
		}
		
		return carbonFootprintDto;
	}

	@Override
	@Transactional
	public void deleteByUserId(String UserId) {
		carbonFootprintRepository.deleteByUserId(UserId);
	}

	@Override
	public CarbonFootprintDTO findHalfYearlySumsByYear(String userId, int year) {
	    CarbonFootprintDTO halfYearlyFootprints = null;

	    List<String> firstHalf = Arrays.asList("January", "February", "March", "April", "May", "June");
	    List<String> secondHalf = Arrays.asList("July", "August", "September", "October", "November", "December");

	    LocalDate currentDate = LocalDate.now();
	    Month currentMonth = currentDate.getMonth();

	    if (currentMonth.getValue() <= Month.JUNE.getValue()) {
	        List<String> firstHalfUpToCurrent = firstHalf.subList(0, currentMonth.getValue());
	        halfYearlyFootprints = calculateTotalForHalfYear(firstHalfUpToCurrent, year, "January-" + currentMonth, userId);
	    } else {
	        List<String> secondHalfUpToCurrent = secondHalf.subList(0, currentMonth.getValue() - Month.JUNE.getValue());
	        halfYearlyFootprints = calculateTotalForHalfYear(secondHalfUpToCurrent, year, "July-" + currentMonth, userId);
	    }

	    return halfYearlyFootprints;
	}
	
	private CarbonFootprintDTO calculateTotalForHalfYear(List<String> months, int year, String halfYearLabel, String userId) {
	    List<CarbonFootprint> footprints = new ArrayList<>();
	    for(String month: months) {
	    	footprints.add(
	    			carbonFootprintRepository
	    			.findByUserIdAndMonthAndYear(userId, month, year)
	    			.orElse(null));
	    }
	    float totalTransportation = 0;
	    float totalElectricity = 0;
	    float totalLpg = 0;
	    float totalShipping = 0;
	    float totalAirConditioner = 0;

	    for (CarbonFootprint footprint : footprints) {
	    	if(footprint == null) continue;
	        totalTransportation += footprint.getTransportation();
	        totalElectricity += footprint.getElectricity();
	        totalLpg += footprint.getLpg();
	        totalShipping += footprint.getShipping();
	        totalAirConditioner += footprint.getAirConditioner();
	    }

	    CarbonFootprintDTO halfYearFootprintDTO = new CarbonFootprintDTO(
	        totalTransportation,
	        totalElectricity,
	        totalLpg,
	        totalShipping,
	        totalAirConditioner
	    );
	    halfYearFootprintDTO.setFootprintMonth(halfYearLabel);
	    halfYearFootprintDTO.setFootprintYear(year);

	    return halfYearFootprintDTO;
	}
	
	@Override
	public List<String> getLast6MonthsFootprintCount(List<String> userIds) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue(); 
        
        for(String userId: userIds) {
        	if(carbonFootprintRepository.countFootprintsForLast6Months(userId, currentYear, currentMonth) != 6) {
        		userIds.remove(userId);
        	}
        }
        return userIds;
    }
}
