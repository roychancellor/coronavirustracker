package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class DailyAccelerationOfCasesWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyAccelerationOfCasesWithMovingAverageChartList.class);

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList) {
		log.debug("MAKING ACCELERATION OF DAILY CASES VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		Map<Integer, Double> dailyAccelCases = new HashMap<>();
		
		double valueToday;
		double valueYesterday;
		double valueDayBeforeYesterday;
		double accelerationOfCases = 0;
		int startIndexForAccelerationOfCases = 2;  //day 2 in list
		for(int dayIndex = startIndexForAccelerationOfCases; dayIndex < regionDataList.size(); dayIndex++) {
			valueDayBeforeYesterday = regionDataList.get(dayIndex - 2).getTotalPositiveCases();
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			accelerationOfCases = ((valueToday - valueYesterday) - (valueYesterday - valueDayBeforeYesterday)) * 100.0 / (valueToday - valueYesterday);
			
			dailyAccelCases.put(dayIndex, accelerationOfCases);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", accelerationOfCases);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(
			ChartListMakerUtilities.makeMovingAverageList(
					dailyAccelCases,
					MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + 1,
					regionDataList.size()
			));

		log.debug("DONE MAKING ACCELERATION OF DAILY CASES VERSUS TIME");

		return scatterChartDataLists;
	}
}
