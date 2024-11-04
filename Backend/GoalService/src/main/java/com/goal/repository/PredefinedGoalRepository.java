package com.goal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goal.model.PredefinedGoal;

public interface PredefinedGoalRepository extends JpaRepository<PredefinedGoal, Long> {
	Optional<PredefinedGoal> findByPredefinedGoalId(Long predefinedGoalId);
}
