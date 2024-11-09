package com.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalInsight {
	
	private long insightId;
	private String userId;
	private String description;
	private LocalDate date;

}
