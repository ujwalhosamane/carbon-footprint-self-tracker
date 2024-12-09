package com.security.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDataCreation {
	private String userId;
	private String name;
	private String email;
	private String city;
	private LocalDateTime creationDate;
}
