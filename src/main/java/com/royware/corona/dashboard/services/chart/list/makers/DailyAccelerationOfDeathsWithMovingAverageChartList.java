package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.ChartListConstants;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DailyAccelerationOfDeathsWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyAccelerationOfDeathsWithMovingAverageChartList.class);

	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
		log.debug("MAKING ACCELERATION OF DAILY DEATHS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		Map<Integer, Double> dailyAccelDeaths = new HashMap<>();
		
		double valueToday;
		double valueYesterday;
		double valueDayBeforeYesterday;
		double accelerationOfDeaths = 0;
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveDeaths(regionDataList);
		int dayIndex = startDayIndex + 2;  //day 2 in list
		while(dayIndex < regionDataList.size()) {
			valueDayBeforeYesterday = regionDataList.get(dayIndex - 2).getTotalDeaths();
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			valueToday = regionDataList.get(dayIndex).getTotalDeaths();
			accelerationOfDeaths = ((valueToday - valueYesterday) - (valueYesterday - valueDayBeforeYesterday)) * 100.0 / (valueToday - valueYesterday);
			
			dailyAccelDeaths.put(dayIndex, accelerationOfDeaths);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", accelerationOfDeaths);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					dailyAccelDeaths,
					startDayIndex + ChartListConstants.MOVING_AVERAGE_SIZE.getValue() + 1,
					regionDataList.size()
				));

		log.debug("DONE MAKING ACCELERATION OF DAILY DEATHS VERSUS TIME");

		return scatterChartDataLists;
	}
}
