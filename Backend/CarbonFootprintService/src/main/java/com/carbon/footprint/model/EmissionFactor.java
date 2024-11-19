package com.carbon.footprint.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmissionFactor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long emissionFactorId;
	private Double transportation;  
    private Double electricity;     
    private Double lpg;            
    private Double shipping;       
    private Double airConditioner;
}
