package com.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionDTO {
	private Long suggestionId;
	private String description;
	private LocalDate creationDate;
}
