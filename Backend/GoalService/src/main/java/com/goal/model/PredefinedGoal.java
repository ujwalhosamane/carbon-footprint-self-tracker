package com.goal.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedGoal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long predefinedGoalId;

	private Date creationDate;
	
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
    
    @NotNull(message = "Badge Url cannot be null")
    private String badgeUrl;
	
    @JsonBackReference
	@OneToMany(mappedBy = "predefinedGoal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Goal> goals;
	
	public PredefinedGoal(@NotNull(message = "Title cannot be null") String title,
				@NotNull(message = "Type cannot be null") String type,
	            @NotNull(message = "Description cannot be null") String description,
	            @NotNull(message = "Target score cannot be null") Long targetScore,
	            @NotNull(message = "Reward point cannot be null") Long rewardPoint,
	            @NotNull(message = "Badge Url cannot be null") String badgeUrl) {
		this.title = title;
		this.type = type;
		this.description = description;
		this.targetScore = targetScore;
		this.rewardPoint = rewardPoint;
		this.badgeUrl = badgeUrl;
	}
}
