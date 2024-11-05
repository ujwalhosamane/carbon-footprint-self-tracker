package com.carbon.footprint.suggestion.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionDTO {
	private String description;
	private LocalDate creationDate;
}
