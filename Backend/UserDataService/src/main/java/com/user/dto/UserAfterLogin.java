package com.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAfterLogin {
	private String name;
	private String email;
	private String city;
	private LocalDate creationDate;
	private Double totalFootprint;
	private Double sixMonthRewardPoints;
	private Double totalRewardPoints;
}
