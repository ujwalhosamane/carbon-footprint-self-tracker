package com.goal.exception;

public class PredefinedGoalNotFoundException extends RuntimeException {
	public PredefinedGoalNotFoundException(String message) {
		super(message);
	}
}
