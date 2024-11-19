package com.admin.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.dto.AdminAfterLogin;

@FeignClient(name = "IDENTITY-SERVICE")
public interface UserIdenetityClient {
	@GetMapping("/admin/latest-created")
    public List<Map<String, Object>> getLatestCreatedUsers();

    @GetMapping("/admin/latest-logins")
    public List<Map<String, Object>> getLatestLoginActivities();
    
    @GetMapping("/admin/current-login-count")
    public Map<String, Long> getLoggedInNonAdminUserCount();
    
    @GetMapping("/admin/get/admin-details")
    public ResponseEntity<AdminAfterLogin> getAdminAfterLogin(@RequestParam("token") String token);
}
