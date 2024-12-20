package com.goal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "predefined_goal_id"}))
public class Goal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long goalId;
	private String userId;
	private Double currentScore;
	private Long count;
	private boolean isAchieved;
	
	@ManyToOne
    @JoinColumn(name = "predefined_goal_id", nullable = false)
    private PredefinedGoal predefinedGoal;
}
