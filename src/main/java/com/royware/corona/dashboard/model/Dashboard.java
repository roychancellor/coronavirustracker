package com.royware.corona.dashboard.model;

public class Dashboard {
	private DashboardChartData chartLists;
	private DashboardChartConfig chartConfig;
	
	public Dashboard(DashboardChartData chartLists, DashboardChartConfig chartConfig) {
		this.chartLists = chartLists;
		this.chartConfig = chartConfig;
	}

	public DashboardChartData getChartLists() {
		return chartLists;
	}

	public void setChartLists(DashboardChartData chartLists) {
		this.chartLists = chartLists;
	}

	public DashboardChartConfig getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(DashboardChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}
}
