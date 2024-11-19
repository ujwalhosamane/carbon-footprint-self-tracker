package com.security.respository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.entity.LoginActivity;

public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {

	@Query("SELECT l FROM LoginActivity l WHERE l.user.role <> 'ADMIN' ORDER BY l.activityDate DESC")
    List<LoginActivity> findLatestLoginActivities(Pageable pageable);
}