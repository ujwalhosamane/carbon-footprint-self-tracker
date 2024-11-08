package com.quartz.scheduler.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "UserService")
public interface UserClient {
	@PutMapping("/get/and/update/rewardPoints/six")
	public ResponseEntity<Void> getAndUpdateSixMonthsRewardPoints();
	
	@PutMapping("/get/and/update/rewardPoints/")
	public ResponseEntity<Void> getAndUpdateRewardPoints();
}
