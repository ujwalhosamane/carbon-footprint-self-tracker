package com.carbon.footprint.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbon.footprint.client.SuggestionClient;
import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.dto.LatestActivity;
import com.carbon.footprint.exception.FootprintDateException;
import com.carbon.footprint.model.CarbonFootprint;
import com.carbon.footprint.repository.CarbonFootprintRepository;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

@Service
public class CarbonFootprintServiceImpl implements CarbonFootprintServiceInterface {

	@Autowired
	private CarbonFootprintRepository carbonFootprintRepository;
	
	@Autowired
	private SuggestionClient suggestionClient;
	
	@Override
	public CarbonFootprintDTO addFootprint(String userId, CarbonFootprintDTO footprintDto, LocalDate accountCreationDate) {
		validateFootprintDate(footprintDto.getFootprintMonth(), footprintDto.getFootprintYear(), accountCreationDate);
		
		float totalFootprint = footprintDto.getTransportation() + footprintDto.getElectricity() +
				footprintDto.getLpg() + footprintDto.getShipping() + footprintDto.getAirConditioner();

		CarbonFootprint carbonFootprint = new CarbonFootprint();
		carbonFootprint.setUserId(userId);
		carbonFootprint.setFootprintMonth(footprintDto.getFootprintMonth());
		carbonFootprint.setFootprintYear(footprintDto.getFootprintYear());
		carbonFootprint.setTransportation(footprintDto.getTransportation());
		carbonFootprint.setElectricity(footprintDto.getElectricity());
		carbonFootprint.setLpg(footprintDto.getLpg());
		carbonFootprint.setShipping(footprintDto.getShipping());
		carbonFootprint.setAirConditioner(footprintDto.getAirConditioner());
		
		carbonFootprint.setTotalFootprint(totalFootprint);
		
		carbonFootprint = carbonFootprintRepository.save(carbonFootprint);
		suggestionClient.addSuggestion(userId, carbonFootprint.getCarbonFootprintId(), footprintDto);
		return carbonFootprint != null ? footprintDto : null;
	}

	@Override
	public List<CarbonFootprintDTO> getFootprintsByUserId(String userId) {
		List<CarbonFootprintDTO> listCarbonFootprintDto = carbonFootprintRepository.findByUserId(userId);
		
		return listCarbonFootprintDto.size() > 0 ?  listCarbonFootprintDto : null;
	}

	@Override
	public void deleteFootprint(String userId, String month, int year) {
		Optional<CarbonFootprint> carbonFootprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(
				userId, month, year);
		if(carbonFootprint.isEmpty()) {
			//handle exception
		}
		
		carbonFootprintRepository.deleteById(carbonFootprint.get().getCarbonFootprintId());
		suggestionClient.deleteByFootprintId(carbonFootprint.get().getCarbonFootprintId());
	}

	@Override
	public List<CarbonFootprintDTO> getAllFootprint() {
		
		return carbonFootprintRepository.findAllAsDTO();
	}

	@Override
	public CarbonFootprintDTO updateFootprint(String userId, CarbonFootprintDTO footprintDto, LocalDate accountCreationDate) {
		CarbonFootprint carbonFootprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(
				userId,
				footprintDto.getFootprintMonth(),
				footprintDto.getFootprintYear()).get();
		
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
			
			carbonFootprint = carbonFootprintRepository.save(carbonFootprint);
			suggestionClient.regenerateAndUpdate(userId, carbonFootprint.getCarbonFootprintId(), footprintDto);
			return carbonFootprint != null ?
					footprintDto : null;
		}
		
		return null;
	}

	@Override
	public CarbonFootprintDTO findByUserIdAndMonthAndYear(String userId, String month, int year) {
		Optional<CarbonFootprint> footprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(userId, month, year);
		if(footprint.isPresent()) {
			return convertToDTO(footprint.get());		
		}
		
		return null;
	}
	
	@Override
	public List<CarbonFootprintDTO> findByMonthAndYear(String month, int year) {
		List<CarbonFootprintDTO> carbonFootprintDto = carbonFootprintRepository.findAllSumsByMonthAndYear(month, year).orElse(null);
//		List<CarbonFootprintDTO> carbonFootprintDto = new ArrayList<>();
//		if(carbonFootprint.size() > 0) {
//			for(CarbonFootprint footprint: carbonFootprint) {
//				CarbonFootprintDTO footprintDto = 
//						new CarbonFootprintDTO(footprint.getTransportation(),
//								footprint.getElectricity(),
//								footprint.getLpg(),
//								footprint.getShipping(),
//								footprint.getAirConditioner());
//				footprintDto.setFootprintMonth(month);
//				footprintDto.setFootprintYear(year);
//				carbonFootprintDto.add(footprintDto);
//			}
//		}
		
		return carbonFootprintDto;
    }

	@Override
	public List<CarbonFootprintDTO> findLastNMonthsSums(int n) {
        List<CarbonFootprintDTO> nMonthsResult = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < n; i++) {
        	Calendar current = (Calendar) calendar.clone();
            current.add(Calendar.MONTH, -i); 

            String month = getMonthName(current.get(Calendar.MONTH)); 
            int year = current.get(Calendar.YEAR);
            
            List<CarbonFootprintDTO> carbonFootprintDto = carbonFootprintRepository.findSumsByMonthAndYear(month, year).orElse(null);
            
            if(carbonFootprintDto.size() > 0) {
            	float totalTransportation = 0;
    			float totalElectricity = 0;
    			float totalLpg = 0;
    			float totalShipping = 0;
    			float totalAirConditioner = 0;
    			for(CarbonFootprintDTO footprint: carbonFootprintDto) {
    				totalTransportation += footprint.getTransportation();
    				totalElectricity += footprint.getElectricity();
    				totalLpg += footprint.getLpg();
    				totalShipping += footprint.getShipping();
    				totalAirConditioner += footprint.getAirConditioner();
    			}
    			
    			CarbonFootprintDTO footprintDto = new CarbonFootprintDTO(totalTransportation, 
    					totalElectricity,
    					totalLpg,
    					totalShipping,
    					totalAirConditioner);
    			footprintDto.setFootprintMonth(month);
    			footprintDto.setFootprintYear(year);
    			
    			nMonthsResult.add(footprintDto);
            }
        }

        return nMonthsResult;
    }
	
	private void validateFootprintDate(String footprintMonth, int footprintYear, LocalDate creationDate) {
//	    int creationYear = creationDate.getYear();
//	    Month creationMonth = creationDate.getMonth();
//
//	    LocalDate currentDate = LocalDate.now();
//	    int currentYear = currentDate.getYear();
//	    Month currentMonth = currentDate.getMonth();
//
//	    Month footprintMonthEnum = Month.valueOf(footprintMonth.toUpperCase());
//
//	    if (footprintYear < creationYear || 
//	        (footprintYear == creationYear && footprintMonthEnum.compareTo(creationMonth) < 0)) {
//	        throw new FootprintDateException("Footprint month and year cannot be earlier than the account creation date.");
//	    }
//
//	    if (footprintYear > currentYear || 
//	        (footprintYear == currentYear && footprintMonthEnum.compareTo(currentMonth) > 0)) {
//	        throw new FootprintDateException("Footprint month and year cannot be later than the current date.");
//	    }
		
		LocalDate currentDate = LocalDate.now();
		LocalDate sixMonthsAgo = currentDate.minusMonths(6);

		Month footprintMonthEnum = Month.valueOf(footprintMonth.toUpperCase());
		LocalDate footprintDate = LocalDate.of(footprintYear, footprintMonthEnum, 1);

		if (footprintDate.isBefore(sixMonthsAgo)) {
		    throw new FootprintDateException("Footprint date cannot be earlier than six months from the current date.");
		}

		if (footprintDate.isAfter(currentDate)) {
		    throw new FootprintDateException("Footprint date cannot be in the future.");
		}
	}

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
        	Calendar current = (Calendar) calendar.clone();
            current.add(Calendar.MONTH, -i); 

            String month = getMonthName(current.get(Calendar.MONTH)); 
            int year = current.get(Calendar.YEAR);
            
            CarbonFootprint carbonFootprint = carbonFootprintRepository.findByUserIdAndMonthAndYear(userId, month, year).orElse(null);
            if(carbonFootprint != null) {
                nMonthsResult.add(convertToDTO(carbonFootprint));
            } else {
            	nMonthsResult.add(null);
            }
        }

        return nMonthsResult;
	}

	@Override
	public CarbonFootprintDTO findAllSumsByUserId(String userId) {
		List<CarbonFootprintDTO> carbonFootprint = carbonFootprintRepository.findByUserId(userId);
		CarbonFootprintDTO carbonFootprintDto = null;
		if(carbonFootprint.size() > 0) {
			float totalTransportation = 0;
			float totalElectricity = 0;
			float totalLpg = 0;
			float totalShipping = 0;
			float totalAirConditioner = 0;
			for(CarbonFootprintDTO footprint: carbonFootprint) {
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
		suggestionClient.removeSuggestionByUserId(UserId);
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
	public List<String> getLast6MonthsFootprintCount(List<String> userIds, Map<String, LocalDate> createdAt) {
		LocalDate currentDate = LocalDate.now();
	    int currentYear = currentDate.getYear();
	    int currentMonth = currentDate.getMonthValue();

	    LocalDate sixMonthsAgo = currentDate.minusMonths(6).withDayOfMonth(1);

	    Iterator<String> iterator = userIds.iterator();
	    while (iterator.hasNext()) {
	        String userId = iterator.next();
	        LocalDate userCreationDate = createdAt.get(userId);

	        if (userCreationDate.isBefore(sixMonthsAgo)) {
	            int footprintCount = carbonFootprintRepository.countFootprintsForLast6Months(userId, currentYear, currentMonth);
	            if (footprintCount != 6) {
	                iterator.remove();
	            }
	        }
	    }

	    return userIds;
    }
	
	public CarbonFootprintDTO getLatestFootprint(String userId) {
        return carbonFootprintRepository.findLatestFootprintByUser(userId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private CarbonFootprintDTO convertToDTO(CarbonFootprint carbonFootprint) {
        return new CarbonFootprintDTO(
                carbonFootprint.getFootprintMonth(),
                carbonFootprint.getFootprintYear(),
                carbonFootprint.getTransportation(),
                carbonFootprint.getElectricity(),
                carbonFootprint.getLpg(),
                carbonFootprint.getShipping(),
                carbonFootprint.getAirConditioner()
        );
    }
    
    public LatestActivity getLatestActivity(String userId) {
        List<Tuple> results = carbonFootprintRepository.findLatestActivityByUserId(userId);
        if(results == null) {
        	new RuntimeException("No activity found");
        }
        Date creationDate, updatedDate;
        String footprintMonth;
        int footprintYear;
        System.out.println(results);
        for (Tuple tuple : results) {
            creationDate = tuple.get("creationDate", Date.class);
            updatedDate = tuple.get("updatedDate", Date.class);
            footprintMonth = tuple.get("footprintMonth", String.class);
            footprintYear = tuple.get("footprintYear", Integer.class);
            return new LatestActivity(creationDate, updatedDate, footprintMonth,footprintYear);
        }
        
        return null;
        
    }
    
    public Map<String, Double> getTotalFootprintForCurrentAndPreviousMonth() {
        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM"));
        int currentYear = currentDate.getYear();

        LocalDate previousMonthDate = currentDate.minusMonths(1);
        String previousMonth = previousMonthDate.format(DateTimeFormatter.ofPattern("MM"));
        int previousYear = previousMonthDate.getYear();

        return carbonFootprintRepository.getTotalFootprintForCurrentAndPreviousMonth(
                Arrays.asList(currentMonth, previousMonth),
                Arrays.asList(currentYear, previousYear)
        );
    }
    
    public void deleteOldFootprints(int retentionDurationMonths) {
        LocalDate currentDate = LocalDate.now();
        LocalDate retentionDate = currentDate.minusMonths(retentionDurationMonths);

        int retentionYear = retentionDate.getYear();
        String retentionMonth = retentionDate.getMonth().toString().toUpperCase(); 
        List<CarbonFootprint> beforeDeletionDTO = carbonFootprintRepository.findAll();
        carbonFootprintRepository.deleteByFootprintYearBeforeOrFootprintYearAndFootprintMonthBefore(retentionYear, retentionMonth);
        List<CarbonFootprint> afterDeletionDTO = carbonFootprintRepository.findAll();
        
        List<Long> footprintIds = findDeletedCarbonFootprintIds(beforeDeletionDTO, afterDeletionDTO);
        suggestionClient.deleteAllByFootprintId(footprintIds);
        
    }
    
    public List<Long> findDeletedCarbonFootprintIds(List<CarbonFootprint> beforeDeletionDTO, List<CarbonFootprint> afterDeletionDTO) {
        Set<Long> afterDeletionIds = afterDeletionDTO.stream()
                .map(CarbonFootprint::getCarbonFootprintId)
                .collect(Collectors.toSet());

        List<Long> deletedIds = beforeDeletionDTO.stream()
                .map(CarbonFootprint::getCarbonFootprintId)
                .filter(id -> !afterDeletionIds.contains(id))
                .collect(Collectors.toList());

        return deletedIds;
    }
    
    public Date getRetentionDate(int retentionDurationMonths) {
    	LocalDate currentDate = LocalDate.now().withDayOfMonth(1);
        LocalDate retentionDate = currentDate.minusMonths(retentionDurationMonths);
        return java.sql.Date.valueOf(retentionDate);
    }
    
    public CarbonFootprintDTO getFootprintDTO(
    	String userId,
    	Long carbonFootprintId) {
    	CarbonFootprint carbonFootprint = carbonFootprintRepository.findById(carbonFootprintId).orElse(null);
    	if(carbonFootprint == null) {
    		return null;
    	}
    	
    	if(!carbonFootprint.getUserId().equals(userId)) {
    		return null;
    	}
    	return new CarbonFootprintDTO(
                carbonFootprint.getFootprintMonth(),
                carbonFootprint.getFootprintYear(),
                carbonFootprint.getTransportation(),
                carbonFootprint.getElectricity(),
                carbonFootprint.getLpg(),
                carbonFootprint.getShipping(),
                carbonFootprint.getAirConditioner()
        );
    }
    
}
