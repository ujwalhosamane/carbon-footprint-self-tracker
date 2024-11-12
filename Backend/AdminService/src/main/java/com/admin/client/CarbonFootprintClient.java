package com.admin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.dto.CarbonFootprintDTO;

@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface CarbonFootprintClient {
	/**
	 * ADMIN
	 */
	@GetMapping("/carbonFootprint/getAll")
	public ResponseEntity<List<CarbonFootprintDTO>> getAllFootprint();
	
	@GetMapping("/carbonFootprint/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year);
	
	@GetMapping("/carbonFootprint/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months);
}
