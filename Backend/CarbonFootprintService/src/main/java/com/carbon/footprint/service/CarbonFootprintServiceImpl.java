package com.carbon.footprint.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbon.footprint.dto.CarbonFootprintDTO;
import com.carbon.footprint.model.CarbonFootprint;
import com.carbon.footprint.repository.CarbonFootprintRepository;

import jakarta.transaction.Transactional;

@Service
public class CarbonFootprintServiceImpl implements CarbonFootprintServiceInterface {

	@Autowired
	private CarbonFootprintRepository carbonFootprintRepository;
	
	@Override
	public CarbonFootprint addFootprint(CarbonFootprintDTO footprintDto) {
			
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
		carbonFootprint.setCreationDate(new Date());
		
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
	public CarbonFootprint updateFootprint(CarbonFootprintDTO footprintDto, Long footprintId) {
		System.out.println(footprintId);
		CarbonFootprint carbonFootprint = carbonFootprintRepository.findByCarbonFootprintId(footprintId);
		
		System.out.println(carbonFootprint.getUserId().getClass());
		System.out.println(footprintDto.getUserId().getClass());
		if(!carbonFootprint.getUserId().equals(footprintDto.getUserId())) {
			carbonFootprint = null;
		}
		
		if(carbonFootprint != null) {
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
			carbonFootprint.setCreationDate(new Date());
		}
		//System.out.println(carbonFootprint.getUserId());
		System.out.println(footprintDto.getUserId());
		System.out.println(carbonFootprint);
		
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
	
	

}
