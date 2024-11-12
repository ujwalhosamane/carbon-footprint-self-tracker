package com.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarbonFootprintDTO {
	private String userId;
	private String footprintMonth;
	private int footprintYear;
	private float transportation;
	private float electricity;
	private float lpg;
	private float shipping;
	private float airConditioner;
	
	public CarbonFootprintDTO(float transportation, float electricity, float lpg, float shipping, float airConditioner) {
        this.transportation = transportation;
        this.electricity = electricity;
        this.lpg = lpg;
        this.shipping = shipping;
        this.airConditioner = airConditioner;
    }
}
