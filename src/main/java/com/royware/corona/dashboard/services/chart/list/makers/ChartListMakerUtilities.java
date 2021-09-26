package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.data.DataTransformConstants;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public class ChartListMakerUtilities {
	private static final Logger log = LoggerFactory.getLogger(ChartListMakerUtilities.class);

	public static Map<Object, Object> makeXYPairWithDateStamp(int xValue, int yValue, String dateStamp) {
		Map<Object, Object> xyPair;
		xyPair = new HashMap<>();
		xyPair.put("x", xValue);
		xyPair.put("y", yValue);
		xyPair.put("dateChecked", dateStamp);
		return xyPair;
	}

	public static <T extends ICanonicalCaseDeathData> int findFirstDayIndexWithPositiveDeaths(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getTotalDeaths() > 0) {
				log.debug("first day index with positive deaths: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getTotalDeaths() + " deaths");
				return dayIndex;
			}
		}
		return 0;
	}
	
	public static <T extends ICanonicalCaseDeathData> int findFirstDayIndexWithPositiveCurrentHospitalizations(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getHospitalizedCurrently() > 0) {
				log.debug("first day index with positive current hospitalizations: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getHospitalizedCurrently() + " hospitalizations");
				return dayIndex;
			}
		}
		return 0;
	}
	
	public static <T extends ICanonicalCaseDeathData> int findFirstDayIndexWithPositiveCumulativeHospitalizations(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getHospitalizedCumulative() > 0) {
				log.debug("first day index with positive cumulative hospitalizations: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getHospitalizedCumulative() + " hospitalizations");
				return dayIndex;
			}
		}
		return 0;
	}
	
	public static List<Map<Object, Object>> makeExponentialLineList(int minCases, int maxCases, double k) {
		List<Map<Object, Object>> expLineList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		xyPair = new HashMap<>();
		xyPair.put("x", minCases);
		xyPair.put("y", k * minCases);
		expLineList.add(xyPair);
		xyPair = new HashMap<>();
		xyPair.put("x", maxCases);
		xyPair.put("y", k * maxCases);
		expLineList.add(xyPair);
		
		return expLineList;
	}
	
	public static List<Map<Object, Object>> makeMovingAverageList(Map<Integer, Double> dataMap, int startDayIndex, int endDayIndex) {
		double movingAverage;
		int divisor = 0;
		double amountToAdd = 0;
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		log.debug("Making the moving average...");
		for(int dayIndex = startDayIndex; dayIndex < endDayIndex; dayIndex++) {
			movingAverage = 0;
			for(int d = dayIndex; d > dayIndex - DataTransformConstants.MOVING_AVERAGE_SIZE.getValue(); d--) {
				amountToAdd = dataMap.get(d);
				if(Double.isNaN(amountToAdd) || Double.isInfinite(amountToAdd) || (int)amountToAdd == 100) {
					log.trace("Oops...amountToAdd is not a real number, it is " + amountToAdd + ", so it will NOT be in the moving average.");
					continue;
				}
				movingAverage += amountToAdd;
				divisor++;
			}
			if(divisor > 0) {
				movingAverage /= divisor;
			}
			divisor = 0;
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", movingAverage);
			movingAverageList.add(xyPair);
		}
		
		return movingAverageList;
	}
}
