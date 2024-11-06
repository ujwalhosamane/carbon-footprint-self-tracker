package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.model.User;
import com.user.model.UserRole;

import feign.Param;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
	// For Predefined Goal
	@Query("SELECT u.userId FROM User u "
			+ "WHERE u.role = 'USER'")
	List<String> getAllNonAdminUserId();
	
	// For Leader Board on Footprint
	@Query("SELECT new com.user.dto.LeaderBoardOnFootprint("
			+ "u.name, u.city, u.totalFootprint) "
			+ "FROM User u "
			+ "WHERE u.role = 'USER' "
			+ "AND u.userId <> :userId")
	List<LeaderBoardOnFootprint> getAllLeaderBoardOnFootprint(
			@Param("userId") String userId);
	
	
	// For Leader Board on Reward Points
	@Query("SELECT new com.user.dto.LeaderBoardOnRewardPoints("
			+ "u.name, u.city, u.totalRewardPoints) "
			+ "FROM User u "
			+ "WHERE u.role = 'USER' "
			+ "AND u.userId <> :userId")
	List<LeaderBoardOnRewardPoints> getAllLeaderBoardOnRewardPoints(
			@Param("userId") String userId);	
}