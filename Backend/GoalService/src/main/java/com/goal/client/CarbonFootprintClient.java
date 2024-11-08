package com.goal.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.goal.dto.CarbonFootprintDTO;

@FeignClient(name = "CARBONFOOTPRINTSERVICE")
public interface CarbonFootprintClient {
	@GetMapping("/carbonFootprint/toGoal/getSums/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getHalfYearSums(@PathVariable String userId, @PathVariable int year);
	
	@GetMapping("/carbonFootprint/count/forAllUserId")
	public ResponseEntity<List<String>> getCountOfFootprint(@RequestBody List<String> userIds);
}
