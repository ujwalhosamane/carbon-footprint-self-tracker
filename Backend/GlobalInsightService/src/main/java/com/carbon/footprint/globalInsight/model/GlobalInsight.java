package com.carbon.footprint.globalInsight.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GlobalInsight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long insightId;
	private long userId;
	private String description;
	private LocalDate date;

}
