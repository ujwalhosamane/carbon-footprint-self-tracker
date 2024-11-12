package com.user.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserData {
	@Id
	private String userId;
	
	@NotNull(message = "Name cannot be null")
	private String name;
	
	@NotNull(message = "City cannot be null")
	@Column(nullable = false) 
	private String city;
    
	@NotNull(message = "Creation Date cannot be null")
	private LocalDate creationDate;
	private Double totalFootprint;
	private Double sixMonthRewardPoints;
	private Double totalRewardPoints;
	
	public UserData(String userId, 
			@NotNull(message = "Name cannot be null") String name,
			@NotNull(message = "City cannot be null") String city,
			@NotNull(message = "Creation Date cannot be null") LocalDate creationDate) {
		super();
		this.userId = userId;
		this.name = name;
		this.city = city;
		this.creationDate = creationDate;
		this.totalFootprint = Double.valueOf(0);
		this.sixMonthRewardPoints = Double.valueOf(0);
		this.totalRewardPoints = Double.valueOf(0);
	}

}
