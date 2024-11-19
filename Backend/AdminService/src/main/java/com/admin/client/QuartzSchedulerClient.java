package com.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "QUARTZ-SCHEDULER-SERVICE")
public interface QuartzSchedulerClient {
	@PutMapping("/admin/setParameter/for-data-retention")
    public String setParameter(@RequestParam Long value);
    
    @GetMapping("/admin/get/for-data-retention")
    public Long getParamter();
}
