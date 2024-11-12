package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.CarbonFootprintClient;
import com.admin.dto.CarbonFootprintDTO;

@RestController
@RequestMapping("/admin/footprint")
public class FootprintController {
	@Autowired
	private CarbonFootprintClient footprintClient;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CarbonFootprintDTO>> getAllFootprint() {
		return footprintClient.getAllFootprint();
	}
	
	@GetMapping("/get/{month}/{year}")
	public ResponseEntity<List<CarbonFootprintDTO>> getSumByMonthAndYear(@PathVariable String month, @PathVariable int year) {
		return footprintClient.getSumByMonthAndYear(month, year);
	}
	
	@GetMapping("/getAllNSums/{months}")
	public ResponseEntity<List<CarbonFootprintDTO>> getNMonthsSum(@PathVariable int months) {
		return footprintClient.getNMonthsSum(months);
	}
}
