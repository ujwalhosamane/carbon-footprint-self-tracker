package com.carbon.footprint.globalInsight.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carbon.footprint.globalInsight.DTO.InsightDTO;
import com.carbon.footprint.globalInsight.model.GlobalInsight;
import com.carbon.footprint.globalInsight.service.InsightService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping("/globalInsight")
public class InsightController {
	@Autowired
	private InsightService service;
	
	@GetMapping("/allInsights")
	public ResponseEntity<List<GlobalInsight>> fetchAllInsight()
	{
		List<GlobalInsight> allInsights = service.getAllInsights();
		if(allInsights != null)
		{
			return ResponseEntity.ok(allInsights);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}	
	}
	
	@PostMapping(value="/addInsight")
	public ResponseEntity<GlobalInsight> addInsight(@RequestBody GlobalInsight insight)
	{
		GlobalInsight addedInsight = service.createInsight(insight);
		return ResponseEntity.status(HttpStatus.OK).body(addedInsight);
	}
	
	@GetMapping(value="/insight/{insightId}")
	public ResponseEntity<Optional<GlobalInsight>> fetchInsightById(@PathVariable("insightId") long insightId)
	{
		Optional<GlobalInsight> theInsight = service.getInsightById(insightId);
		if(theInsight != null)
		{
			return ResponseEntity.ok(theInsight);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value="/description/{insightId}")
	public ResponseEntity<String> fetchDescriptionById(@PathVariable("insightId") long insightId)
	{
		String description = service.getDescriptionById(insightId);
		return ResponseEntity.ok(description);
	}
	
	@GetMapping(value="/recentDescriptions/{days}")
	public  ResponseEntity<List<InsightDTO>> fetchRecentDescriptions(@PathVariable("days") int days)
	{
		List<InsightDTO> recentdescriptions = service.getRecentDescriptions(days);
		return ResponseEntity.ok(recentdescriptions);
		
	}
	
	@GetMapping("/getNInsights/{n}")
	public ResponseEntity<List<String>> getNInsights(@PathVariable int n) {
		return new ResponseEntity<>(service.fetchTopNByDate(n), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteInsight/{insightId}")
	public String removeInsightById(@PathVariable("insightId") long insightId)
	{
		service.deleteInsightbyId(insightId);
		return "insight deleted";
	}
	
	@PutMapping(value="/updateInsight")
	public ResponseEntity<GlobalInsight> updateInsight(@RequestBody GlobalInsight insight) {	
		GlobalInsight updatedInsight = service.updateInsight(insight);
		return ResponseEntity.ok(updatedInsight);
	}
	
	@GetMapping(value="/descriptionbydate/{date}")
	public ResponseEntity<List<GlobalInsight>> fetchDescriptionByDate(@PathVariable("date") LocalDate date)
	{
		
			List<GlobalInsight> descriptions = service.getDescriptionByDate(date);
			if(!descriptions.isEmpty())
			{
				return ResponseEntity.ok(descriptions);	
			}
			else
			{
				throw new RuntimeException("there is no insight entry for requested date : "+date+" ");
			}
	}
}
