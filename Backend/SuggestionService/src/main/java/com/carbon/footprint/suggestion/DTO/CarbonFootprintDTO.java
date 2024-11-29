package com.carbon.footprint.suggestion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarbonFootprintDTO {
	private String footprintMonth;
	private int footprintYear;
	private float transportation;
	private float electricity;
	private float lpg;
	private float shipping;
	private float airConditioner;
	
	@Override
	public String toString() {
		return "The air conditioner carbon footprint is " + airConditioner +
		           ", transportation is " + transportation +
		           ", electricity is " + electricity +
		           ", LPG is " + lpg +
		           ", and shipping is " + shipping +
		           " for the month of " + footprintMonth +
		           " and the year " + footprintYear + ".";
	}
}
