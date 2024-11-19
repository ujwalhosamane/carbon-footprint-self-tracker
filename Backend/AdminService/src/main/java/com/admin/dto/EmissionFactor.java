package com.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmissionFactor {
	private Long emissionFactorId;
	private Double transportation;  
    private Double electricity;     
    private Double lpg;            
    private Double shipping;       
    private Double airConditioner;
}
