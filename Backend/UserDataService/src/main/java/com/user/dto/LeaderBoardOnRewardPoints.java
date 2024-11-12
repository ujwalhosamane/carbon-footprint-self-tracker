package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardOnRewardPoints {
	private String name;
	private String city;
	private Double totalRewardPoints;
	private boolean isCurrentUser = false;
	
	public LeaderBoardOnRewardPoints(String name, String city, Double totalRewardPoints) {
		this.name = name;
		this.city = city;
		this.totalRewardPoints = totalRewardPoints;
	}
}
