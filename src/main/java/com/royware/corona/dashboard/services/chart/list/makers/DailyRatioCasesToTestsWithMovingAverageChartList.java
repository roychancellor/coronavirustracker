package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.DataTransformConstants;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DailyRatioCasesToTestsWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyRatioCasesToTestsWithMovingAverageChartList.class);
	private Map<Integer, Double> dailyRatioOfTests = new HashMap<>();

	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
		log.debug("MAKING RATIO OF CASES TO TESTS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		this.dailyRatioOfTests.clear();
		int valueYesterday;
		int valueToday;
		int testsToday = 0;
		int testsYesterday = 0;
		double ratio = 0.0;
		int newCases = 0;
		int newTests = 0;
		int dayIndex = 1;
		while(dayIndex < regionDataList.size()) {
			testsToday = regionDataList.get(dayIndex).getTotalPositiveCases() + regionDataList.get(dayIndex).getTotalNegativeCases();
			testsYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases() + regionDataList.get(dayIndex - 1).getTotalNegativeCases();
			newTests = testsToday - testsYesterday;
			if(newTests == 0) {
				dailyRatioOfTests.put(dayIndex, 0.0);
				dayIndex++;
				continue;
			}
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			newCases = valueToday - valueYesterday;
			ratio = newCases * 100.0 / newTests;
			dailyRatioOfTests.put(dayIndex, ratio);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", ratio);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					dailyRatioOfTests,
					DataTransformConstants.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));

		log.debug("DONE MAKING RATIO OF CASES TO TESTS VERSUS TIME");

		return scatterChartDataLists;
	}
}
