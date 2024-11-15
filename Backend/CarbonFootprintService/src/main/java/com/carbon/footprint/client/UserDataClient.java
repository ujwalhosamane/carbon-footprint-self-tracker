package com.carbon.footprint.client;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GOAL-SERVICE")
public interface UserDataClient {
	@GetMapping("/user/get/createdAt")
    public Map<String, LocalDate> getUserIsWithCreatedAt(@RequestBody List<String> userIds);
}
