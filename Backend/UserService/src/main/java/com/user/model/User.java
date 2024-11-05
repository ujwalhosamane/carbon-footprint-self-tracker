package com.user.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Id
	private String userId;
	
	private String name;
	private String email;
	private String password;
	private String role;
	private LocalDate creationDate;
	private Double totalFootprint;
	private Double totalRewardPoints;
	
	@PrePersist
    protected void generateUserId() {
		userId = UUID.randomUUID().toString();
    }
}
