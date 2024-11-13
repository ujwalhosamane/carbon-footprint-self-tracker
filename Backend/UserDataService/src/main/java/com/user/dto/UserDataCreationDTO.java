package com.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDataCreationDTO {
	private String userId;
	private String name;
	private String email;
	private String city;
	private LocalDate creationDate;
}
