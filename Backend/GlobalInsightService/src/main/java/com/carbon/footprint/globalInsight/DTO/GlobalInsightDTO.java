package com.carbon.footprint.globalInsight.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalInsightDTO {
	
	private long insightId;
	private String description;
	private LocalDate date;

}
