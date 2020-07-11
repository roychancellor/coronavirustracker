package com.royware.corona.dashboard.services.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

public class DownloadChartData {
	public static void downloadChartData(String region, ModelMap map, HttpServletResponse response) {
		response.setContentType("text/csv");
		
		StringBuilder sb = new StringBuilder();
		sb.append(region);
		sb.append(System.currentTimeMillis());
		sb.append(".csv");
		String filename = sb.toString();
		
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		
		List<String> rows = new ArrayList<String>();
		rows.add("First,Last");
		rows.add("\n");
 
		for (int i = 0; i < 10; i++) {
			rows.add("Roy,Chancellor");
			rows.add("\n");
		}
 
		try {
			for(String row : rows) {
				response.getOutputStream().print(row);
			}
	//		Iterator<String> iter = rows.iterator();
	//		while (iter.hasNext()) {
	//			String outputString = (String) iter.next();
	//			response.getOutputStream().print(outputString);
	//		}
	 
			response.getOutputStream().flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
