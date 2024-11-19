package com.security.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role <> 'ADMIN' ORDER BY u.creationDate DESC")
    List<User> findLatestCreatedUsers(Pageable pageable);
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.isLoggedIn = true AND u.role != 'ADMIN'")
    long countLoggedInNonAdminUsers();
}
