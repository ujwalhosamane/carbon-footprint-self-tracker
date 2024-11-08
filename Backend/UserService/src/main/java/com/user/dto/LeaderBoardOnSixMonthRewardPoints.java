package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardOnSixMonthRewardPoints {
	private String name;
	private String city;
	private Double sixMonthRewardPoints;
	private boolean isCurrentUser = false;
	
	public LeaderBoardOnSixMonthRewardPoints(String name, String city, Double sixMonthRewardPoints) {
		super();
		this.name = name;
		this.city = city;
		this.sixMonthRewardPoints = sixMonthRewardPoints;
	}
	
	
}
