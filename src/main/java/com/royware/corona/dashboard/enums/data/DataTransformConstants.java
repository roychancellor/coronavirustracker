package com.royware.corona.dashboard.enums.data;

public enum DataTransformConstants {
	MOVING_AVERAGE_SIZE(4),
	CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY(7),
	CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY(10),
	CURRENT_DEATHS_QUEUE_SIZE_PRIMARY(7),
	CURRENT_DEATHS_QUEUE_SIZE_SECONDARY(10),
	PER_CAPITA_BASIS(100000),
	JUMP_FILTER_PERCENT_THRESHOLD_PCT(50),
	MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION(100),
	US_CUTOFF_DATE(20200304);
	
	public final Integer size;
	
	private DataTransformConstants(Integer size) {
		this.size = size;
	}
	
	public Integer getValue() {
		return this.size;
	}
}
