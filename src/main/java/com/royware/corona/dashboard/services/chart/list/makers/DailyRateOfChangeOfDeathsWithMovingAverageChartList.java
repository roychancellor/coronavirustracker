package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.ChartListConstants;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DailyRateOfChangeOfDeathsWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyRateOfChangeOfDeathsWithMovingAverageChartList.class);
	private Map<Integer, Double> dailyPctChgDeaths = new LinkedHashMap<>();

	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
		log.debug("MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		dailyPctChgDeaths.clear();
		
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveDeaths(regionDataList) + 1;
		log.debug("startDayIndex for daily deaths: " + startDayIndex);
		int valueToday;
		int valueYesterday;
		double percentChange = 0;
		int dayIndex = startDayIndex;
		while(dayIndex < regionDataList.size()) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			valueToday = regionDataList.get(dayIndex).getTotalDeaths();
			percentChange = (valueToday - valueYesterday) * 100.0 / valueYesterday;
			dailyPctChgDeaths.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					dailyPctChgDeaths,
					startDayIndex + ChartListConstants.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));

		log.debug("DONE MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME");

		return scatterChartDataLists;
	}
}
