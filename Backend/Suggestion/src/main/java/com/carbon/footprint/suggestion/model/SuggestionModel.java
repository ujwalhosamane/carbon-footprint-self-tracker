package com.carbon.footprint.suggestion.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private long suggestionId;
	private long userId;
	private String description;
	private LocalDate creationDate;
}
