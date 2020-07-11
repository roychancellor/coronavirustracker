package com.royware.corona.dashboard.model.dashboard;

public class Dashboard {
	private DashboardChartData chartLists;
	private DashboardChartConfig chartConfig;
	
	public Dashboard(Builder builder) {
		this.chartLists = builder.chartLists;
		this.chartConfig = builder.chartConfig;
	}

	public DashboardChartData getChartLists() {
		return chartLists;
	}

	public DashboardChartConfig getChartConfig() {
		return chartConfig;
	}

	public static class Builder {
		private DashboardChartData chartLists;
		private DashboardChartConfig chartConfig;
		
		public Builder setChartLists(DashboardChartData chartLists) {
			this.chartLists = chartLists;
			return this;
		}
	
		public Builder setChartConfig(DashboardChartConfig chartConfig) {
			this.chartConfig = chartConfig;
			return this;
		}
		
		public Dashboard build() {
			return new Dashboard(this);
		}
	}
}
