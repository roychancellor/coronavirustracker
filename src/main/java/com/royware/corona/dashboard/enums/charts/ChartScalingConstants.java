package com.royware.corona.dashboard.enums.charts;

public enum ChartScalingConstants {
	DAYS_THRESHOLD_FOR_Y_MAX(35);
	
	public final Integer value;
	
	private ChartScalingConstants(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
}
