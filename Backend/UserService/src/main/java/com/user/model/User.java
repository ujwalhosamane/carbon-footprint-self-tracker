package com.user.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
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
	
	@NotNull(message = "Name cannot be null")
	private String name;
	
	@NotNull(message = "Email cannot be null")
	@Column(unique = true)
	private String email;
	
	@NotNull(message = "Password cannot be null")
	@Column(nullable = false) 
    private String password;
	
	@NotNull(message = "City cannot be null")
	@Column(nullable = false) 
	private String city;
    
	@NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
	private LocalDate creationDate;
	private Double totalFootprint;
	private Double sixMonthRewardPoints;
	private Double totalRewardPoints;
	
	@PrePersist
    protected void generateUserId() {
		userId = UUID.randomUUID().toString();
    }

	public User(@NotNull(message = "Name cannot be null") String name,
			@NotNull(message = "Email cannot be null") String email,
			@NotNull(message = "Password cannot be null") String password,
			@NotNull(message = "City cannot be null") String city) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.city = city;
	}
}
