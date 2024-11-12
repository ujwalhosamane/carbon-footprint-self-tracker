package com.quartz.scheduler.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "USER-DATA-SERVICE")
public interface UserClient {
	@PutMapping("/get/and/update/rewardPoints/six")
	public ResponseEntity<Void> getAndUpdateSixMonthsRewardPoints();
	
	@PutMapping("/get/and/update/rewardPoints/")
	public ResponseEntity<Void> getAndUpdateRewardPoints();
}
