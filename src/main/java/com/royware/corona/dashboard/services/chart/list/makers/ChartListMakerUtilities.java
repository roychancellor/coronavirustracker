package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.data.ChartListConstants;
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
			for(int d = dayIndex; d > dayIndex - ChartListConstants.MOVING_AVERAGE_SIZE.getValue(); d--) {
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

	public static List<Map<Object, Object>> filterJumpsFromList(List<Map<Object, Object>> toFilter) {
		// Method that takes out "large" jumps from day to day that are the result of bad data in the provided data sets
		List<Map<Object, Object>> filteredList = new ArrayList<>(toFilter);
		
		if(filteredList.size() == 2) {
			return filteredList;
		}
		
		List<Double> yValues = new ArrayList<>();
		for (Map<Object, Object> yValue : filteredList) {
			yValues.add(Double.valueOf(yValue.get("y").toString()));
		}
		Collections.sort(yValues);
		
		int size = yValues.size();
		boolean sizeIsEven = size % 2 == 0;
//		double median = 0.0;
		double Q1 = 0.0;
		double Q3 = 0.0;
		int iMedian = size / 2;
		int iQ1 = 0;
		int iQ3 = 0;
		double IQR = 0.0;
		double upper = 0.0;
		double lower = 0.0;
		if(sizeIsEven) {
			//median = (yValues.get(iMedian - 1) + yValues.get(iMedian)) / 2.0;
			iQ1 = (iMedian - 1) / 2;
			iQ3 = 3 * iMedian / 2;
			Q1 = yValues.get(iQ1);
			Q3 = yValues.get(iQ3);
		} else {
			//median = yValues.get(iMedian);
			iQ1 = iMedian / 2;
			iQ3 = 3 * iMedian / 2;
			Q1 = (yValues.get(iQ1) + yValues.get(iQ1 - 1)) / 2.0;
			Q3 = (yValues.get(iQ3) + yValues.get( + 1)) / 2.0;
		}
		IQR = Q3 - Q1;
		upper = Q3 + 1.5 * IQR;
		lower = Q1 - 1.5 * IQR;
		
		log.info("Making the filtered list with lower = " + lower + " and upper = " + upper);
		
//		double absChangePct = 0.0;
//		Object prevDayValue = 0.0;
		Double todayValue = 0.0;
//		Object prevOriginalValue = 0.0;
		
		for(int dayIndex = 1; dayIndex < filteredList.size(); dayIndex++) {
			// Look for outliers
			todayValue = yValues.get(dayIndex);
//			todayValue = Double.valueOf(filteredList.get(dayIndex).get("y").toString());
			if(todayValue < lower || todayValue > upper) {
				log.info("Removing value: " + todayValue);
				filteredList.remove(dayIndex);
			}
			
			// Look for data points where the change from the previous day is greater than X%			
//			todayValue = Double.valueOf(filteredList.get(dayIndex).get("y").toString());
//			prevDayValue = Double.valueOf(filteredList.get(dayIndex - 1).get("y").toString());
//			prevOriginalValue = filteredList.get(dayIndex - 1).get("y");
//			absChangePct = Math.abs(((Double)todayValue / (Double)prevDayValue - 1) * 100.0);
//			if(absChangePct > ChartListConstants.JUMP_FILTER_PERCENT_THRESHOLD_PCT.getValue()) {
//				log.info("Removing value: " + todayValue);
//				filteredList.get(dayIndex).put("y", prevOriginalValue);
//				continue;
//			}
		}
			
		return filteredList;
	}
	
	public static <T extends ICanonicalCaseDeathData> List<T> cleanDataList(List<T> regionData) {
		// Method will clean out data where the daily change in a total quantity is negative.
		List<T> toReturn = new ArrayList<T>(regionData);
		
//		int today = toReturn.get(0).getTotalPositiveCases();
//		for(int i = 1; i < toReturn.size(); i++) {
//			
//		}
		
		return toReturn;
	}	
}
