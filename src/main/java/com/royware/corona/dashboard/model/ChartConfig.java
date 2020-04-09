package com.royware.corona.dashboard.model;

public class ChartConfig {
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String chartType;
	private String xAxisType;
	private String yAxisType;
	private String xAxisPosition;
	private String yAxisPosition;
	
	public ChartConfig() {
		
	}
	
	public ChartConfig(String chartTitle, String xAxisTitle, String yAxisTitle, String chartType) {
		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
		this.chartType = chartType;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	public String getyAxisTitle() {
		return yAxisTitle;
	}

	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getxAxisType() {
		return xAxisType;
	}

	public void setxAxisType(String xAxisType) {
		this.xAxisType = xAxisType;
	}

	public String getyAxisType() {
		return yAxisType;
	}

	public void setyAxisType(String yAxisType) {
		this.yAxisType = yAxisType;
	}

	public String getxAxisPosition() {
		return xAxisPosition;
	}

	public void setxAxisPosition(String xAxisPosition) {
		this.xAxisPosition = xAxisPosition;
	}

	public String getyAxisPosition() {
		return yAxisPosition;
	}

	public void setyAxisPosition(String yAxisPosition) {
		this.yAxisPosition = yAxisPosition;
	}
	
	
	
}
