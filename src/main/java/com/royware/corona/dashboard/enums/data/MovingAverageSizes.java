package com.royware.corona.dashboard.enums.data;

public enum MovingAverageSizes {
	MOVING_AVERAGE_SIZE(4),
	CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY(7),
	CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY(10),
	CURRENT_DEATHS_QUEUE_SIZE_PRIMARY(7),
	CURRENT_DEATHS_QUEUE_SIZE_SECONDARY(10),
	PER_CAPITA_BASIS(100000);
	
	public final Integer size;
	
	private MovingAverageSizes(Integer size) {
		this.size = size;
	}
	
	public Integer getValue() {
		return this.size;
	}
}
