package com.carbon.footprint.suggestion.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuggestionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long suggestionId;
	private String userId;
	@Lob
	private String description;
	
	@CreationTimestamp
	private LocalDate creationDate;
	
	private Long carbonFootprintId;

	public SuggestionModel(String userId, String description, Long carbonFootprintId) {
		super();
		this.userId = userId;
		this.description = description;
		this.carbonFootprintId = carbonFootprintId;
	}
}
