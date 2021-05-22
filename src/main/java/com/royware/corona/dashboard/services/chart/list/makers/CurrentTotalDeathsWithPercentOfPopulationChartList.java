package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class CurrentTotalDeathsWithPercentOfPopulationChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(CurrentTotalDeathsWithPercentOfPopulationChartList.class);

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int regionPopulation) {
		log.debug("MAKING CURRENT TOTAL DEATHS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		Map<Object, Object> xyPairSec;
		List<Map<Object, Object>> dataListPrimary = new ArrayList<>();
		List<Map<Object, Object>> dataListSecondary = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//Makes a list of total current deaths for each day on a rolling basis
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveDeaths(regionDataList);
		Queue<Integer> dailyChangeInDeathsPrimary = new LinkedList<>();
		Queue<Integer> dailyChangeInDeathsSecondary = new LinkedList<>();
		dailyChangeInDeathsPrimary.add(regionDataList.get(startDayIndex).getTotalDeaths());
		dailyChangeInDeathsSecondary.add(regionDataList.get(startDayIndex).getTotalDeaths());
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		int rollingSumPrimary = 0;
		int rollingSumSecondary = 0;
		int dayIndex = startDayIndex + 1;
		while(dayIndex < regionDataList.size()) {
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			totalToday = regionDataList.get(dayIndex).getTotalDeaths();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;

			if(dayIndex < startDayIndex + MovingAverageSizes.CURRENT_DEATHS_QUEUE_SIZE_PRIMARY.getValue()) {
				rollingSumPrimary = totalToday;
			} else {
				rollingSumPrimary += dailyChange - dailyChangeInDeathsPrimary.peek();
				dailyChangeInDeathsPrimary.remove();
			}
			dailyChangeInDeathsPrimary.add(dailyChange);
			
			if(dayIndex < startDayIndex + MovingAverageSizes.CURRENT_DEATHS_QUEUE_SIZE_SECONDARY.getValue()) {
				rollingSumSecondary = totalToday;
			} else {
				rollingSumSecondary += dailyChange - dailyChangeInDeathsSecondary.peek();
				dailyChangeInDeathsSecondary.remove();
			}
			dailyChangeInDeathsSecondary.add(dailyChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", rollingSumPrimary * MovingAverageSizes.PER_CAPITA_BASIS.getValue() * 1.0 / regionPopulation);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataListPrimary.add(xyPair);
			
			xyPairSec = new HashMap<>();
			xyPairSec.put("x", dayIndex);
			xyPairSec.put("y", rollingSumSecondary * MovingAverageSizes.PER_CAPITA_BASIS.getValue() * 1.0 / regionPopulation);
			dataListSecondary.add(xyPairSec);
			dayIndex++;
		}
		scatterChartDataLists.add(dataListPrimary);
		scatterChartDataLists.add(dataListSecondary);
				
		log.debug("DONE MAKING CURRENT TOTAL DEATHS VERSUS TIME");

		return scatterChartDataLists;
	}
}
