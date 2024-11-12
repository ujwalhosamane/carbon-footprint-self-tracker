package com.user.service;

import java.time.LocalDate;
import java.util.Map;

public interface ClientServiceInterface {

	LocalDate getUserCreationDateByUserId(String userId);

	void updateUserTotalFootprint(String userId, Double totalFootprint);
	void updateSixMonthRewardPoints(Map<String, Double> userRewardPoints);
	void updateTotalRewardPoints(Map<String, Double> userTotalRewardPoints);
}
