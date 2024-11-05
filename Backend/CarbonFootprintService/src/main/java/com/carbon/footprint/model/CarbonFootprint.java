package com.carbon.footprint.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
	    name = "carbon_footprint",
	    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "footprintMonth", "footprintYear"})
	  )
@Entity
public class CarbonFootprint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carbonFootprintId;
	private String userId;
	
	private LocalDate creationDate;
	
	private String footprintMonth;
	private int footprintYear;
	private float transportation;
	private float electricity;
	private float lpg;
	private float shipping;
	private float airConditioner;
	private float totalFootprint;
	
	public CarbonFootprint(float transportation, float electricity, float lpg, float shipping, float airConditioner) {
        this.transportation = transportation;
        this.electricity = electricity;
        this.lpg = lpg;
        this.shipping = shipping;
        this.airConditioner = airConditioner;
    }
}
