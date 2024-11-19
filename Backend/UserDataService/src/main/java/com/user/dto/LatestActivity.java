package com.user.dto;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestActivity {
	private Date creationDate;
    private Date updatedDate;
	private String footprintMonth;
	private int footprintYear;
}
