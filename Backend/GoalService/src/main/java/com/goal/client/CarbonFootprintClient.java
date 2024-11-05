package com.goal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goal.dto.CarbonFootprintDTO;

@FeignClient(name = "CARBONFOOTPRINTSERVICE")
public interface CarbonFootprintClient {
	@GetMapping("/carbonFootprint/toGoal/getSums/{year}/{userId}")
	public ResponseEntity<CarbonFootprintDTO> getHalfYearSums(@PathVariable String userId, @PathVariable int year);
}
