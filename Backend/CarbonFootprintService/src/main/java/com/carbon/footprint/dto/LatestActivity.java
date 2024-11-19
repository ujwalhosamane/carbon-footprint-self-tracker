package com.carbon.footprint.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LatestActivity {
	private Date creationDate;
    private Date updatedDate;
	private String footprintMonth;
	private int footprintYear;
	
	public LatestActivity(Date creationDate, Date updatedDate, String footprintMonth, int footprintYear) {
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.footprintMonth = footprintMonth;
        this.footprintYear = footprintYear;
    }
}
