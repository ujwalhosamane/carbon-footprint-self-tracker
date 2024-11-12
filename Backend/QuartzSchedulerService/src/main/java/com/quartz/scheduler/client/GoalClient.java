package com.quartz.scheduler.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "GOAL-SERVICE")
public interface GoalClient {
	@PutMapping("/goal/reset/score")
	public ResponseEntity<Void> resetCurrentScore();
	
	@PutMapping("/goal/update/achievement")
	public ResponseEntity<Void> updateAchievement();
}
