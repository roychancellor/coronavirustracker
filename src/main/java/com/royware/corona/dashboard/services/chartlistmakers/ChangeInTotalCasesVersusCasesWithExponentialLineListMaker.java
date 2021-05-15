package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.interfaces.charts.ChartListMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.services.charts.ChartListMakerUtilities;

@Component
public class ChangeInTotalCasesVersusCasesWithExponentialLineListMaker implements ChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(ChangeInTotalCasesVersusCasesWithExponentialLineListMaker.class);
	private Map<Integer, Double> dailyChgCases = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeList(List<T> regionDataList) {
		log.debug("MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		int valueToday;
		int valueYesterday;
		double changeInCases = 0;
		double maxChangeInCases = regionDataList.get(0).getTotalPositiveCases();
		dailyChgCases.clear();
		int startIndexForDailyChangeOfCases = 1; //day 1 in list
		for(int dayIndex = startIndexForDailyChangeOfCases; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			changeInCases = valueToday - valueYesterday;
			
			dailyChgCases.put(dayIndex, changeInCases);
			
			if(changeInCases > maxChangeInCases) {
				maxChangeInCases = changeInCases;
			}
			
			xyPair = new HashMap<>();
			xyPair.put("x", (valueToday + valueYesterday) / 2);  //use average of yesterday and today as the current case number
			xyPair.put("y", changeInCases);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.30998; //need to compute this for each dataset using regression on a subset of the data (early days)
		int minCases = regionDataList.get(0).getTotalPositiveCases();
		if(minCases <= 0) {
			minCases = 1;
		}
		int maxCases = regionDataList.get(regionDataList.size() - 1).getTotalPositiveCases();
		if(maxCases <= 0) {
			maxCases = 10;
		}
		scatterChartDataLists.add(ChartListMakerUtilities.makeExponentialLineList(minCases, maxCases, k));

		log.debug("DONE MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES");

		return scatterChartDataLists;
	}

}
