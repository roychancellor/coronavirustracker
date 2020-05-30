package com.royware.corona.dashboard.model.dashboard;

import java.util.List;
import java.util.Map;

public class DashboardChartData {
	private List<List<Map<Object, Object>>> chartLists;

	public DashboardChartData() {
	}
	
	public DashboardChartData(List<List<Map<Object, Object>>> chartLists) {
		this.chartLists = chartLists;
	}

	public List<List<Map<Object, Object>>> getChartLists() {
		return chartLists;
	}

	public void setChartLists(List<List<Map<Object, Object>>> chartLists) {
		this.chartLists = chartLists;
	}

}
