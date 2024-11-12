package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
	private Long goalId;
	private String userId;
	private Double currentScore;
	private Long count;
	private boolean isAchieved;
}
