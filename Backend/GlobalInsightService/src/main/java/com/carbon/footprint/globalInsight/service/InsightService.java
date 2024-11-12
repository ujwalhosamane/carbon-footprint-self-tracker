package com.carbon.footprint.globalInsight.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carbon.footprint.globalInsight.DTO.InsightDTO;
import com.carbon.footprint.globalInsight.model.GlobalInsight;
import com.carbon.footprint.globalInsight.repository.InsightRepository;

@Service
public class InsightService {
	
	@Autowired
	private InsightRepository repository;
	
	public GlobalInsight createInsight(GlobalInsight insight)
	{
		return repository.save(insight);	
	}
	
	public Optional<GlobalInsight> getInsightById(long insightId)
	{
		return repository.findById(insightId);
	}
	
	public List<GlobalInsight> getAllInsights()
	{
		return repository.findAll();
	}
	
	public String getDescriptionById(long insightId) {
        return repository.findById(insightId)
                .map(GlobalInsight::getDescription)
                .orElse(null);
    }
	public List<GlobalInsight> getDescriptionByDate(LocalDate date)
	{
		List<GlobalInsight> descriptionForDate= repository.findByDate(date);
		return descriptionForDate;
	}
	
	public List<InsightDTO> getRecentDescriptions(int ndays)
	{
		LocalDate lastDate = LocalDate.now().minusDays(ndays);
		List<GlobalInsight> insights = repository.findByDateAfter(lastDate);
		return insights
				.stream()
				 .map(insight -> new InsightDTO(
                         insight.getDescription(),
                         insight.getDate()))
                     .collect(Collectors.toList());
	}
	
	public GlobalInsight updateInsight(GlobalInsight updatedInsight) {
	    if (!repository.existsById(updatedInsight.getInsightId())) {
	        throw new RuntimeException("Insight not found with ID " + updatedInsight.getInsightId());
	    }
	    return repository.save(updatedInsight);
	}
	
	public void deleteInsightbyId(long insightId)
	{
		repository.deleteById(insightId);
	}
	 
	public List<String> fetchTopNByDate(int n) {
	    Pageable pageable = PageRequest.of(0, n); 
	    return repository.findAllByOrderByDateDesc(pageable)
	    		.stream()
	            .map(GlobalInsight::getDescription)
	            .collect(Collectors.toList());
	}
}
