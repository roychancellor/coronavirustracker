package com.royware.corona.dashboard.model;

import java.util.List;
import java.util.Map;

public class DashboardChart {
	private List<List<Map<Object, Object>>> chartLists;
	private ChartConfig chartConfig;
	
	public DashboardChart(List<List<Map<Object, Object>>> chartLists, ChartConfig chartConfig) {
		this.chartLists = chartLists;
		this.chartConfig = chartConfig;
	}

	public List<List<Map<Object, Object>>> getChartLists() {
		return chartLists;
	}

	public void setChartLists(List<List<Map<Object, Object>>> chartLists) {
		this.chartLists = chartLists;
	}

	public ChartConfig getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(ChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}
}
