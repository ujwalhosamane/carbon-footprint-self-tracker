package com.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.QuartzSchedulerClient;

@RestController
@RequestMapping("/admin")
public class QuartzSchedulerController {
	@Autowired
	private QuartzSchedulerClient quartzSchedulerClient;
	
	@PutMapping("/update/for-data-retention/{value}")
    public ResponseEntity<Map<String, String>> setParameter(@PathVariable("value") Long value) {
		return new ResponseEntity<>(Map.of("message", quartzSchedulerClient.setParameter(value)), HttpStatus.OK);
	}
    
    @GetMapping("/get/for-data-retention")
    public ResponseEntity<Map<String, Long>> getParamter() {
    	return new ResponseEntity<>(Map.of("count", quartzSchedulerClient.getParamter()), HttpStatus.OK);
    }
}
