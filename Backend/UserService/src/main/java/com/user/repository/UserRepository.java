package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.user.dto.LeaderBoardOnFootprint;
import com.user.dto.LeaderBoardOnRewardPoints;
import com.user.dto.LeaderBoardOnSixMonthRewardPoints;
import com.user.model.User;
import com.user.model.UserRole;

import feign.Param;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUserIdAndEmail(String userId, String email);
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
	@Query("SELECT new com.user.dto.LeaderBoardOnSixMonthRewardPoints("
			+ "u.name, u.city, u.sixMonthRewardPoints) "
			+ "FROM User u "
			+ "WHERE u.role = 'USER' "
			+ "AND u.userId <> :userId")
	List<LeaderBoardOnSixMonthRewardPoints> getAllLeaderBoardOnSixMonthRewardPoints(
			@Param("userId") String userId);
	
	
	@Query("SELECT new com.user.dto.LeaderBoardOnRewardPoints("
			+ "u.name, u.city, u.totalRewardPoints) "
			+ "FROM User u "
			+ "WHERE u.role = 'USER' "
			+ "AND u.userId <> :userId")
	List<LeaderBoardOnRewardPoints> getAllLeaderBoardOnRewardPoints(
			@Param("userId") String userId);
	
	
	@Query("SELECT u.userId FROM User u "
			+ "WHERE u.email = :email AND u.role = :role")
	Optional<String> getUserIdByEmail(
			@Param("email") String email,
			@Param("role") UserRole role);
	
	
	@Modifying
    @Transactional
    @Query("UPDATE User u "
    		+ "SET u.sixMonthRewardPoints = :sixMonthRewardPoints "
    		+ "WHERE u.userId = :userId")
    void updateSixMonthRewardPoints(String userId, Double sixMonthRewardPoints);
	
	
	@Modifying
    @Transactional
    @Query("UPDATE User u "
    		+ "SET u.totalRewardPoints = :totalRewardPoints "
    		+ "WHERE u.userId = :userId")
    void updateTotlaRewardPoints(String userId, Double totalRewardPoints);
	
	void deleteByUserIdAndEmailAndRole(String userId, String email, UserRole role);
}