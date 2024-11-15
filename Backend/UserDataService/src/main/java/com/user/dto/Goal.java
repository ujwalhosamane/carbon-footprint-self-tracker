package com.user.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
	private Long goalId;
	private Double currentScore;
	private Long count;
	private boolean isAchieved;
	
	private Date creationDate;
	private String title;
	private String type;
    private String description;
    private Long targetScore;
    private Long rewardPoint; 
    private String badgeUrl;
}
