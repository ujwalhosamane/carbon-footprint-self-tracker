package com.goal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goal.dto.GoalDTO;
import com.goal.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
	
	@Query("SELECT new com.goal.dto.GoalDTO(g.goalId, g.currentScore, g.count, g.isAchieved, "
            + "pg.creationDate, pg.title, pg.type, pg.description, pg.targetScore, pg.rewardPoint, pg.badgeUrl) "
            + "FROM Goal g JOIN g.predefinedGoal pg WHERE g.userId = :userId")
    List<GoalDTO> findDtoByUserId(@Param("userId") String userId);
	
	List<Goal> findByUserId(String userId);
	
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
