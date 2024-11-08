package com.goal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
	
	List<Goal>findByUserId(String userId);
	
	void deleteByUserId(String userId);
	
	 @Query("SELECT DISTINCT g.userId FROM Goal g")
	 List<String> findAllDistinctUserIds();
	 
	 @Query("SELECT g.userId, SUM(g.count * pg.rewardPoint) " +
		       "FROM Goal g " +
		       "JOIN g.predefinedGoal pg " +
		       "WHERE g.isAchieved = true " +
		       "GROUP BY g.userId")
	 List<Object[]> findUserIdsWithSixMonthsRewardPoints();
	 
	 @Query("SELECT g.userId, SUM(g.count * pg.rewardPoint) " +
		       "FROM Goal g " +
		       "JOIN g.predefinedGoal pg " +
		       "GROUP BY g.userId")
	 List<Object[]> findUserIdsWithTotalRewardPoints();
}
