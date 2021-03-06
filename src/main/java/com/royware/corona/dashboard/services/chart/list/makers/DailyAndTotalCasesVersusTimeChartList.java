package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DailyAndTotalCasesVersusTimeChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyAndTotalCasesVersusTimeChartList.class);
	private Map<Integer, Double> dailyNewCases = new HashMap<>();

	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
		log.debug("MAKING TOTAL AND DAILY CASES VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//First day
		xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(0, regionDataList.get(0).getTotalPositiveCases(), regionDataList.get(0).getDateChecked().toString());
		dataList.add(xyPair);
		
		//All subsequent days
		int dayIndex = 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(
					dayIndex,
					regionDataList.get(dayIndex).getTotalPositiveCases(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			totalToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;
			dailyNewCases.put(dayIndex, dailyChange * 1.0);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily quantity to smooth out some of the noise
		scatterChartDataLists.add(
			ChartListMakerUtilities.makeMovingAverageList(
					dailyNewCases,
					MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
			));
		
		log.debug("DONE MAKING TOTAL AND DAILY CASES VERSUS TIME");

		return scatterChartDataLists;
	}

}
