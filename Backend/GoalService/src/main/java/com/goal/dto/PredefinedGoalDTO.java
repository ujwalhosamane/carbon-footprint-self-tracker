package com.goal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedGoalDTO {
	@NotNull(message = "Title cannot be null")
    private String title;
	
	@NotNull(message = "Type cannot be null")
	private String type;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Target score cannot be null")
    private Long targetScore;

    @NotNull(message = "Reward point cannot be null")
    private Long rewardPoint;
}
