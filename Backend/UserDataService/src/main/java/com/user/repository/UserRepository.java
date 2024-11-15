package com.user.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.model.UserData;

import feign.Param;

public interface UserRepository extends JpaRepository<UserData, String> {
	Optional<UserData> findByUserId(String userId);
	// For Predefined Goal
	@Query("SELECT u.userId FROM UserData u ")
	List<String> getAllNonAdminUserId();
	
	// For Leader Board on Footprint
	@Query("SELECT new com.user.dto.LeaderBoardOnFootprint("
			+ "u.name, u.city, u.totalFootprint) "
			+ "FROM UserData u "
			+ "WHERE u.userId <> :userId")
	List<LeaderBoardOnFootprint> getAllLeaderBoardOnFootprint(
			@Param("userId") String userId);
	
	
	// For Leader Board on Reward Points
	@Query("SELECT new com.user.dto.LeaderBoardOnSixMonthRewardPoints("
			+ "u.name, u.city, u.sixMonthRewardPoints) "
			+ "FROM UserData u "
			+ "WHERE u.userId <> :userId")
	List<LeaderBoardOnSixMonthRewardPoints> getAllLeaderBoardOnSixMonthRewardPoints(
			@Param("userId") String userId);
	
	
	@Query("SELECT new com.user.dto.LeaderBoardOnRewardPoints("
			+ "u.name, u.city, u.totalRewardPoints) "
			+ "FROM UserData u "
			+ "WHERE u.userId <> :userId")
	List<LeaderBoardOnRewardPoints> getAllLeaderBoardOnRewardPoints(
			@Param("userId") String userId);
	
	
	@Query("SELECT DISTINCT u.userId FROM UserData u")
	List<String> getAllDistinctUserIds();
	
	@Modifying
    @Transactional
    @Query("UPDATE UserData u "
    		+ "SET u.sixMonthRewardPoints = :sixMonthRewardPoints "
    		+ "WHERE u.userId = :userId")
    void updateSixMonthRewardPoints(String userId, Double sixMonthRewardPoints);
	
	
	@Modifying
    @Transactional
    @Query("UPDATE UserData u "
    		+ "SET u.totalRewardPoints = :totalRewardPoints "
    		+ "WHERE u.userId = :userId")
    void updateTotlaRewardPoints(String userId, Double totalRewardPoints);
	
	void deleteByUserId(String userId);
	
	@Query("SELECT COUNT(u) FROM UserData u WHERE YEAR(u.creationDate) = :year AND MONTH(u.creationDate) = :month")
    long countByCreationMonth(@Param("year") int year, @Param("month") int month);
	
	@Query("SELECT u.userId AS userId, u.creationDate AS creationDate FROM UserData u WHERE u.userId IN :userIds")
    Map<String, LocalDate> findCreationDatesByUserIds(@Param("userIds") List<String> userIds);
}