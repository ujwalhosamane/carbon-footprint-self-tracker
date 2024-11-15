package com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.UserClient;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserClient userClient;
	
	@GetMapping("/this-month/users/count")
    public ResponseEntity<Long> getThisMonthUserCount() {
		return new ResponseEntity<Long>(userClient.getThisMonthUserCount(), HttpStatus.OK);
	}

    @GetMapping("/last-month/users/count")
    public ResponseEntity<Long> getLastMonthUserCount() {
    	return new ResponseEntity<Long>(userClient.getLastMonthUserCount(), HttpStatus.OK);
    }
    
    @GetMapping("/total/users/count")
    public ResponseEntity<Long> getTotalUserCount() {
    	return new ResponseEntity<Long>(userClient.getTotalUserCount(), HttpStatus.OK);
    }
}
