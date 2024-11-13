package com.admin.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedGoal {
	
	private Long predefinedGoalId;
	private Date creationDate;
    private String title;
	private String type;
    private String description;
    private Long targetScore;
    private Long rewardPoint;
    private String badgeUrl;
}
