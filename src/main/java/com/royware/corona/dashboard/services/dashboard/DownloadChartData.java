package com.royware.corona.dashboard.services.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.royware.corona.dashboard.model.dashboard.DashboardChart;

public class DownloadChartData {
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
		
		List<String> rows = new ArrayList<String>();
		//Make the data headers
		rows.add("First,Last," + dashboardCharts.get(0).getChartData().getCsvHeader());
		rows.add("\n");
 
		//Make the data rows
		for (int i = 0; i < 10; i++) {
			rows.add("Roy,Chancellor," + dashboardCharts.get(0).getChartData().getChartLists().get(0).get(0).get("y"));
			rows.add("\n");
		}
 
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
}
