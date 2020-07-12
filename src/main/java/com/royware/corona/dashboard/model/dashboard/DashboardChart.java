package com.royware.corona.dashboard.model.dashboard;

public class DashboardChart {
	private String region;
	private DashboardChartData chartData;
	private DashboardChartConfig chartConfig;
	
	public DashboardChart(Builder builder) {
		this.region = builder.region;
		this.chartData = builder.chartData;
		this.chartConfig = builder.chartConfig;
	}

	public DashboardChartData getChartData() {
		return chartData;
	}

	public DashboardChartConfig getChartConfig() {
		return chartConfig;
	}

	public String getRegion() {
		return region;
	}

	public static class Builder {
		private String region;
		private DashboardChartData chartData;
		private DashboardChartConfig chartConfig;
		
		public Builder setRegion(String region) {
			this.region = region;
			return this;
		}

		public Builder setChartData(DashboardChartData chartData) {
			this.chartData = chartData;
			return this;
		}
	
		public Builder setChartConfig(DashboardChartConfig chartConfig) {
			this.chartConfig = chartConfig;
			return this;
		}
		
		public DashboardChart build() {
			return new DashboardChart(this);
		}
	}
}
