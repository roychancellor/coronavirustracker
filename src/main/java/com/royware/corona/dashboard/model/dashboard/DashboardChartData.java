package com.royware.corona.dashboard.model.dashboard;

import java.util.List;
import java.util.Map;

public class DashboardChartData {
	private String csvHeader;
	private List<List<Map<Object, Object>>> chartLists;

	private DashboardChartData() {
	}
	
	private DashboardChartData(Builder builder) {
		this.csvHeader = builder.csvHeader;
		this.chartLists = builder.chartLists;
	}

	public String getCsvHeader() {
		return csvHeader;
	}

	public List<List<Map<Object, Object>>> getChartLists() {
		return chartLists;
	}
	
	public static class Builder {
		private String csvHeader;
		private List<List<Map<Object, Object>>> chartLists;
		
		public Builder withChartDataLists(List<List<Map<Object, Object>>> chartLists) {
			this.chartLists = chartLists;
			return this;
		}
	
		public Builder withCsvHeader(String csvHeader) {
			this.csvHeader = csvHeader;
			return this;
		}
		
		public DashboardChartData build() {
			return new DashboardChartData(this);
		}
	}
	
}
