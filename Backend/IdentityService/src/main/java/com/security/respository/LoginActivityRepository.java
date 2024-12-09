package com.security.respository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.entity.LoginActivity;

public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {

//	@Query("SELECT l FROM LoginActivity l WHERE l.user.role <> 'ADMIN' ORDER BY l.activityDate DESC")
//    List<LoginActivity> findLatestLoginActivities(Pageable pageable);
	
	@Query(value = "SELECT l FROM LoginActivity l " +
            "WHERE l.user.role <> 'ADMIN' AND l.activityDate = " +
            "(SELECT MAX(l2.activityDate) FROM LoginActivity l2 WHERE l2.user.userId = l.user.userId) " +
            "ORDER BY l.activityDate DESC")
	List<LoginActivity> findLatestLoginActivities(Pageable pageable);
}