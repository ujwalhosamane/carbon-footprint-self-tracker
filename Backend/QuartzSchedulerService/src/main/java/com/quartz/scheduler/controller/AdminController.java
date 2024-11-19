package com.quartz.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quartz.scheduler.entity.AdminParameter;
import com.quartz.scheduler.repository.AdminParameterRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private AdminParameterRepository adminParameterRepository;

    @PutMapping("/setParameter/for-data-retention")
    public String setParameter(@RequestParam Long value) {
        AdminParameter parameter = new AdminParameter();
        String key = "retentionDurationInMonths";
        parameter.setParameterKey(key);
        if(value <= 7) {
        	value = Long.valueOf(7);
        }
        parameter.setParameterValue(value.toString());
        adminParameterRepository.save(parameter);
        return "Parameter updated successfully!";
    }
    
    @GetMapping("/get/for-data-retention")
    public Long getParamter() {
    	String key = "retentionDurationInMonths";
    	return Long.parseLong(adminParameterRepository.findById(key).get().getParameterValue());
    }
}
