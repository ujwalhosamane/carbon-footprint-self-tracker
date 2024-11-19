package com.goal.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goal.model.PredefinedGoal;

public interface PredefinedGoalRepository extends JpaRepository<PredefinedGoal, Long> {
	Optional<PredefinedGoal> findByPredefinedGoalId(Long predefinedGoalId);
	
	@Query("SELECT p.title AS title, p.type AS type, p.creationDate AS createdDate " +
	           "FROM PredefinedGoal p " +
	           "ORDER BY p.creationDate DESC")
	List<Map<String, Object>> getLatestPredefinedGoals();
	
	@Query("SELECT new map(p.type as type, p.title as title, p.description as description, COUNT(g.goalId) as totalCount) " +
		       "FROM PredefinedGoal p " +
		       "LEFT JOIN p.goals g " +
		       "GROUP BY p.type, p.predefinedGoalId")
	List<Map<String, Object>> findPredefinedGoalsWithGoalCount(Pageable pageable);
}
