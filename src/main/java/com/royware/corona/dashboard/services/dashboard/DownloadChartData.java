package com.royware.corona.dashboard.services.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.charts.ChartCsvHeaders;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;

import javafx.scene.chart.XYChart.Series;

public class DownloadChartData {
	private static final Logger log = LoggerFactory.getLogger(DownloadChartData.class);
	
	public static void downloadChartData(List<DashboardChart> dashboardCharts, HttpServletResponse response) {
		
		//Build the file name (region + current time in milliseconds + .csv)
		StringBuilder sb = new StringBuilder();
		sb.append(dashboardCharts.get(0).getRegion());
		sb.append(System.currentTimeMillis());
		sb.append(".csv");
		String filename = sb.toString();
		
		//Set the content type so the browser knows what type of data it is downloading
		response.setContentType("text/csv");
		//Set the HTTP header so the browser knows it is downloading a file
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		
		//Make the header row
		List<String> rows = new ArrayList<>();
		rows.add(makeCsvHeaderRow(dashboardCharts));
		//Make the data rows
		rows.addAll(makeCsvDataRows(dashboardCharts));
 
		//Write the data to a CSV file
		try {
			for(String row : rows) {
				response.getOutputStream().print(row);
			}
			//Flush the output stream which will cause the data to be written out to the file
			response.getOutputStream().flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static String makeCsvHeaderRow(List<DashboardChart> dashboardCharts) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("dayIndex,");
		for(int i = 0; i < dashboardCharts.size(); i++) {
			if(dashboardCharts.get(i).getChartData().getCsvHeader().equals(ChartCsvHeaders.CASES_CHG_BY_CASES.getName())
					|| dashboardCharts.get(i).getChartData().getCsvHeader().equals(ChartCsvHeaders.DEATHS_CHG_BY_CASES.getName())) {
				continue;
			}
			sb.append(dashboardCharts.get(i).getChartData().getCsvHeader());
			if(i == dashboardCharts.size() - 1) {
				break; //so there is no comma on the end
			}
			sb.append(",");
		}
		sb.append("\n");
		return sb.toString();
	}

	private static List<String> makeCsvDataRows(List<DashboardChart> dashboardCharts) {
		List<String> dataRows = new ArrayList<String>();
		
		//Get the maximum x value from the cases time history list
		int dayIndexMax = dashboardCharts.get(0).getChartData().getChartLists().get(0).size() - 1;

		int indexOffset = 0;
		for(int dayIndex = 0; dayIndex < dayIndexMax; dayIndex++) {
			StringBuilder sb = new StringBuilder();
			sb.append(dayIndex);
			sb.append(",");
			for(DashboardChart chart : dashboardCharts) {
				List<Map<Object, Object>> dataList = chart.getChartData().getChartLists().get(0);
				indexOffset = dayIndexMax - (dataList.size() - 1);
				if(chart.getChartData().getCsvHeader().equals(ChartCsvHeaders.CASES_CHG_BY_CASES.getName())
						|| chart.getChartData().getCsvHeader().equals(ChartCsvHeaders.DEATHS_CHG_BY_CASES.getName())) {
					continue;
				}
				if(dayIndex - indexOffset >= 0 && (int)dataList.get(dayIndex - indexOffset).get("x") == dayIndex) {
					sb.append(dataList.get(dayIndex - indexOffset).get("y"));
				}
				sb.append(",");
			}
			dataRows.add(sb.toString());
			dataRows.add("\n");
		}
		
		return dataRows;
	}
}
