package com.quartz.scheduler.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CARBON-FOOTPRINT-SERVICE")
public interface FootprintClient {
	@DeleteMapping("/carbonFootprint/deleteOldFootprints/{retentionDurationMonths}")
    public ResponseEntity<String> deleteOldFootprints(@PathVariable int retentionDurationMonths);
}
