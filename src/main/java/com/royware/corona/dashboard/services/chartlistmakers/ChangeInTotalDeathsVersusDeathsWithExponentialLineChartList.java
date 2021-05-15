package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.interfaces.charts.IChartList;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.services.charts.ChartListMakerUtilities;

@Component
public class ChangeInTotalDeathsVersusDeathsWithExponentialLineChartList implements IChartList {
	private static final Logger log = LoggerFactory.getLogger(ChangeInTotalDeathsVersusDeathsWithExponentialLineChartList.class);
	private Map<Integer, Double> dailyChgDeaths = new LinkedHashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList) {
		log.debug("MAKING CHANGE IN DAILY DEATHS VERSUS TOTAL DEATHS");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//Find the first object having a totalDeaths > 0 for two consecutive days
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveDeaths(regionDataList);		
		int valueToday;
		int valueYesterday;
		double changeInDeaths = 0;
		double maxChangeInDeaths = regionDataList.get(0).getTotalDeaths();
		dailyChgDeaths.clear();
		int dayIndex = startDayIndex + 1;
		while(dayIndex < regionDataList.size()) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			valueToday = regionDataList.get(dayIndex).getTotalDeaths();
			changeInDeaths = valueToday - valueYesterday;
			
			dailyChgDeaths.put(dayIndex, changeInDeaths);
			
			if(changeInDeaths > maxChangeInDeaths) {
				maxChangeInDeaths = changeInDeaths;
			}
			
			xyPair = new HashMap<>();
			xyPair.put("x", (valueToday + valueYesterday) / 2);  //use average of yesterday and today as the current case number
			xyPair.put("y", changeInDeaths);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.29966;  //need to compute this for region using regression on early data
		int minCases = regionDataList.get(0).getTotalDeaths();
		if(minCases <= 0) {
			minCases = 1;
		}
		int maxCases = regionDataList.get(regionDataList.size() - 1).getTotalDeaths();
		if(maxCases <= 0) {
			maxCases = 10;
		}
		scatterChartDataLists.add(ChartListMakerUtilities.makeExponentialLineList(minCases, maxCases, k));

		log.debug("DONE MAKING CHANGE IN DAILY DEATHS VERSUS TOTAL DEATHS");

		return scatterChartDataLists;
	}
}
