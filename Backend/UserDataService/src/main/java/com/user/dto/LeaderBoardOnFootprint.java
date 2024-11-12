package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardOnFootprint {
	private String name;
	private String city;
	private Double totalFootprint;
	private boolean isCurrentUser = false;
	
	public LeaderBoardOnFootprint(String name, String city, Double totalFootprint) {
		this.name = name;
		this.city = city;
		this.totalFootprint = totalFootprint;
	}
}
