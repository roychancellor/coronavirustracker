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
public class CurrentTotalPositivesWithPercentOfPopulationChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(CurrentTotalPositivesWithPercentOfPopulationChartList.class);
	
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int regionPopulation) {
		log.debug("MAKING CURRENT TOTAL POSITIVES VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		Map<Object, Object> xyPairSec;
		List<Map<Object, Object>> dataListPrimary = new ArrayList<>();
		List<Map<Object, Object>> dataListSecondary = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//Makes a list of total current cases for each day on a rolling basis
		Queue<Integer> dailyChangeInPositivesPrimary = new LinkedList<>();
		Queue<Integer> dailyChangeInPositivesSecondary = new LinkedList<>();
		dailyChangeInPositivesPrimary.add(regionDataList.get(0).getTotalPositiveCases());
		dailyChangeInPositivesSecondary.add(regionDataList.get(0).getTotalPositiveCases());
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		long rollingSumPrimary = 0;
		long rollingSumSecondary = 0;
		int dayIndex = 1;
		while(dayIndex < regionDataList.size()) {
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			totalToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;

			if(dayIndex < MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue()) {
				rollingSumPrimary = totalToday;
			} else {
				rollingSumPrimary += dailyChange - dailyChangeInPositivesPrimary.peek();
				dailyChangeInPositivesPrimary.remove();
			}
			dailyChangeInPositivesPrimary.add(dailyChange);
			
			if(dayIndex < MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue()) {
				rollingSumSecondary = totalToday;
			} else {
				rollingSumSecondary += dailyChange - dailyChangeInPositivesSecondary.peek();
				dailyChangeInPositivesSecondary.remove();
			}
			dailyChangeInPositivesSecondary.add(dailyChange);
			
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
				
		log.debug("DONE MAKING CURRENT TOTAL POSITIVES VERSUS TIME");

		return scatterChartDataLists;
	}
}
