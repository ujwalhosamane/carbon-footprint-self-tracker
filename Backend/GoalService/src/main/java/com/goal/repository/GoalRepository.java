package com.goal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
	List<Goal> findByUserId(String userId);
	void deleteByUserId(String userId);
}