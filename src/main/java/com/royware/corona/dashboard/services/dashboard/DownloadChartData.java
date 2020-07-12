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

public class DownloadChartData {
	private static final Logger log = LoggerFactory.getLogger(DownloadChartData.class);
	private static final String COMMA = ",";
	private static final String CRLF = "\n";
	
	public static void downloadChartData(String regionType, List<DashboardChart> dashboardCharts, HttpServletResponse response) {
		
		//Build the file name (region + current time in milliseconds + .csv)
		StringBuilder sb = new StringBuilder();
		sb.append(dashboardCharts.get(0).getRegion());
		sb.append("_");
		sb.append(System.currentTimeMillis());
		sb.append(".csv");
		String filename = sb.toString();
		
		//Set the content type so the browser knows what type of data it is downloading
		response.setContentType("text/csv");
		//Set the HTTP header so the browser knows it is downloading a file
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		
		//Make the header row
		log.info("Writing the header row...");
		List<String> rows = new ArrayList<>();
		rows.add(makeCsvHeaderRow(regionType, dashboardCharts));
		//Make the data rows
		log.info("Writing the data rows...");
		rows.addAll(makeCsvDataRows(regionType, dashboardCharts));
 
		//Write the data to a CSV file
		try {
			for(String row : rows) {
				response.getOutputStream().print(row);
			}
			//Flush the output stream which will cause the data to be written out to the file
			response.getOutputStream().flush();
			log.info("DONE downloading the data file: " + filename);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static String makeCsvHeaderRow(String regionType, List<DashboardChart> dashboardCharts) {
		StringBuilder sb = new StringBuilder();
		int worldDataOffset = regionType.equals("world") ? 4 : 0;
		
		sb.append("dayIndex,");
		sb.append("dateChecked,");
		for(int i = 0; i < dashboardCharts.size() - worldDataOffset; i++) {
			if(dashboardCharts.get(i).getChartData().getCsvHeader().equals(ChartCsvHeaders.CASES_CHG_BY_CASES.getName())
					|| dashboardCharts.get(i).getChartData().getCsvHeader().equals(ChartCsvHeaders.DEATHS_CHG_BY_CASES.getName())) {
				continue;
			}
			sb.append(dashboardCharts.get(i).getChartData().getCsvHeader());
			sb.append(COMMA);
		}
		sb.deleteCharAt(sb.length() - 1);  //so there is no trailing comma
		log.info("The header row is: " + sb.toString());
		sb.append(CRLF);
		return sb.toString();
	}

	private static List<String> makeCsvDataRows(String regionType, List<DashboardChart> dashboardCharts) {
		List<String> dataRows = new ArrayList<String>();
		
		//Get the maximum x value from the cases time history list
		int dayIndexMax = dashboardCharts.get(0).getChartData().getChartLists().get(0).size() - 1;
		
		//If the region type is world, delete the last four dashboard charts from the list, as these data are not real for world regions
		if(regionType.equals("world")) {
			for(int i = 0; i < 4; i++) {
				dashboardCharts.remove(dashboardCharts.size() - 1);
			}
		}

		int indexOffset = 0;
		for(int dayIndex = 0; dayIndex < dayIndexMax; dayIndex++) {
			StringBuilder sb = new StringBuilder();
			sb.append(dayIndex);
			sb.append(COMMA);
			//Get the data checked from the cases time series, which is the first list in the first chart
			sb.append(dashboardCharts.get(0).getChartData().getChartLists().get(0).get(dayIndex).get("dateChecked"));
			sb.append(COMMA);
			//Iterate through all of the dashboard charts and get the data for the primary (first -> index 0) chart list
			for(DashboardChart chart : dashboardCharts) {
				List<Map<Object, Object>> dataList = chart.getChartData().getChartLists().get(0);
				//Determine the index offset of the current chart list from the cases list
				indexOffset = dayIndexMax - (dataList.size() - 1);
				//Don't include the log-log DQ / Q chart data
				if(chart.getChartData().getCsvHeader().equals(ChartCsvHeaders.CASES_CHG_BY_CASES.getName())
						|| chart.getChartData().getCsvHeader().equals(ChartCsvHeaders.DEATHS_CHG_BY_CASES.getName())) {
					continue;
				}
				if(dayIndex - indexOffset >= 0 && (int)dataList.get(dayIndex - indexOffset).get("x") == dayIndex) {
					//Check for NaN or +/-Infinity; if found, just write a comma
					Double value = Double.valueOf(dataList.get(dayIndex - indexOffset).get("y").toString());
					if(!(value.isNaN() || value.isInfinite())) {
						sb.append(dataList.get(dayIndex - indexOffset).get("y"));
					}
				}
				sb.append(COMMA);
			}
			sb.deleteCharAt(sb.length() - 1); //delete the trailing comma
			log.info("The data row is: " + sb.toString());
			dataRows.add(sb.toString());
			dataRows.add(CRLF);
		}
		
		return dataRows;
	}
}
