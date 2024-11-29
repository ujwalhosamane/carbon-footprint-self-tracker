package com.carbon.footprint.suggestion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carbon.footprint.suggestion.DTO.CarbonFootprintDTO;


@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface FootrpintClient {
	@GetMapping("/carbonFootprint/get")
	public ResponseEntity<CarbonFootprintDTO> getDto(
			@RequestParam("userId") String userId,
			@RequestParam("carbonFootprintId") Long carbonFootprintId);
}
