package com.royware.corona.dashboard.model;

public class ChartConfig {
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	private String xAxisLogarithmic;
	private String yAxisLogarithmic;
	private String chartType;
	private String xAxisPosition;
	private String yAxisPosition;
	private String xAxisGridlinesDisplay;
	private String yAxisGridlinesDisplay;
	private String showLegend;
	private int dataPointSize;
	private String dataPointStyle;
	private String dataPointColorString;
	private int lineWidth;
	private int lineColor;
	
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

	public String getxAxisLogarithmic() {
		return xAxisLogarithmic;
	}

	public void setxAxisLogarithmic(String xAxisLogarithmic) {
		this.xAxisLogarithmic = xAxisLogarithmic;
	}

	public String getyAxisLogarithmic() {
		return yAxisLogarithmic;
	}

	public void setyAxisLogarithmic(String yAxisLogarithmic) {
		this.yAxisLogarithmic = yAxisLogarithmic;
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

	public String isxAxisGridlinesDisplay() {
		return xAxisGridlinesDisplay;
	}

	public void setxAxisGridlinesDisplay(String xAxisGridlinesDisplay) {
		this.xAxisGridlinesDisplay = xAxisGridlinesDisplay;
	}

	public String isyAxisGridlinesDisplay() {
		return yAxisGridlinesDisplay;
	}

	public void setyAxisGridlinesDisplay(String yAxisGridlinesDisplay) {
		this.yAxisGridlinesDisplay = yAxisGridlinesDisplay;
	}

	public String isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(String showLegend) {
		this.showLegend = showLegend;
	}

	public int getDataPointSize() {
		return dataPointSize;
	}

	public void setDataPointSize(int dataPointSize) {
		this.dataPointSize = dataPointSize;
	}

	public String getDataPointStyle() {
		return dataPointStyle;
	}

	public void setDataPointStyle(String dataPointStyle) {
		this.dataPointStyle = dataPointStyle;
	}

	public String getDataPointColorString() {
		return dataPointColorString;
	}

	public void setDataPointColorString(String dataPointColorString) {
		this.dataPointColorString = dataPointColorString;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}
	
	
	
}
