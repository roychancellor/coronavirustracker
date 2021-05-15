package com.royware.corona.dashboard.services.chartlists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.IChartList;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.services.charts.ChartListMakerUtilities;

@Component
public class DailyRateOfChangeOfCasesWithMovingAverageChartList implements IChartList {
	private static final Logger log = LoggerFactory.getLogger(DailyRateOfChangeOfCasesWithMovingAverageChartList.class);
	private Map<Integer, Double> dailyPctChgCases = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeList(List<T> regionDataList) {
		log.debug("MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		int valueToday;
		int valueYesterday;
		double percentChange = 0;
		this.dailyPctChgCases.clear();
		int startIndexForRateOfChangeOfCases = 1; //day 1 in list
		for(int dayIndex = startIndexForRateOfChangeOfCases; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			percentChange = (valueToday - valueYesterday) * 100.0 / valueYesterday;
			
			dailyPctChgCases.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(
			ChartListMakerUtilities.makeMovingAverageList(
					dailyPctChgCases,
					MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
			));

		log.debug("DONE MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME");

		return scatterChartDataLists;
	}

}
