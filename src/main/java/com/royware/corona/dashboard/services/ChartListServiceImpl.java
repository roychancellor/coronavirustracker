package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.ChartListService;
import com.royware.corona.dashboard.interfaces.CanonicalCases;
 
@Service
public class ChartListServiceImpl implements ChartListService {
	private static final Logger log = LoggerFactory.getLogger(ChartListServiceImpl.class);
	private static final int MOVING_AVERAGE_SIZE = 4;
	private Map<Integer, Double> dailyPctChgCases = new HashMap<>();
	private Map<Integer, Double> dailyChgCases = new HashMap<>();
	private Map<Integer, Double> dailyPctChgDeaths = new LinkedHashMap<>();
	private Map<Integer, Double> dailyChgDeaths = new LinkedHashMap<>();
	
	//CASES WILL ALWAYS START WITH INDEX = 0 --> DAY = 0
	//DEATHS WILL HAVE A VARIABLE-INDEX --> DAY = 0 (DEATHS LAG CASES AND VARIES AMONG REGIONS)
	
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		log.info("***** MAKING TOTAL CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<Map<Object, Object>> expFitList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		int dayIndex = 0;
		for(CanonicalCases cc : regionCaseList) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", cc.getTotalPositiveCases());
			dataList.add(xyPair);
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//TODO: Figure out how to make the exponential regression fit data and populate it here
		//For now, just make a generic exponential model: 100*exp(0.15*t)
		dayIndex = 0;
		while(dayIndex < regionCaseList.size()) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", 100 * Math.exp(0.15 * dayIndex));
			expFitList.add(xyPair);
			dayIndex++;
		}
		scatterChartDataLists.add(expFitList);
		
		log.info("***** DONE MAKING TOTAL CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		log.info("***** MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		int valueToday;
		int valueYesterday;
		double percentChange = 0;
		this.dailyPctChgCases.clear();
		int startIndexForRateOfChangeOfCases = 1; //day 1 in list
		for(int dayIndex = startIndexForRateOfChangeOfCases; dayIndex < regionCaseList.size(); dayIndex++) {
			valueYesterday = regionCaseList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionCaseList.get(dayIndex).getTotalPositiveCases();
			percentChange = (valueToday - valueYesterday) * 100.0 / valueYesterday;
			
			dailyPctChgCases.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyPctChgCases, MOVING_AVERAGE_SIZE, regionCaseList.size()));

		log.info("***** DONE MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList) {
		log.info("***** MAKING ACCELERATION OF DAILY CASES VERSUS TIME *****");
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
		for(int dayIndex = startIndexForAccelerationOfCases; dayIndex < regionCaseList.size(); dayIndex++) {
			valueDayBeforeYesterday = regionCaseList.get(dayIndex - 2).getTotalPositiveCases();
			valueYesterday = regionCaseList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionCaseList.get(dayIndex).getTotalPositiveCases();
			accelerationOfCases = ((valueToday - valueYesterday) - (valueYesterday - valueDayBeforeYesterday)) * 100.0 / (valueToday - valueYesterday);
			
			dailyAccelCases.put(dayIndex, accelerationOfCases);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", accelerationOfCases);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyAccelCases, MOVING_AVERAGE_SIZE + 1, regionCaseList.size()));

		log.info("***** DONE MAKING ACCELERATION OF DAILY CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}
	
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> regionCaseList) {
		log.info("***** MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		int valueToday;
		int valueYesterday;
		double changeInCases = 0;
		double maxChangeInCases = regionCaseList.get(0).getTotalPositiveCases();
		dailyChgCases.clear();
		int startIndexForDailyChangeOfCases = 1; //day 1 in list
		for(int dayIndex = startIndexForDailyChangeOfCases; dayIndex < regionCaseList.size(); dayIndex++) {
			valueYesterday = regionCaseList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionCaseList.get(dayIndex).getTotalPositiveCases();
			changeInCases = valueToday - valueYesterday;
			
			dailyChgCases.put(dayIndex, changeInCases);
			
			if(changeInCases > maxChangeInCases) {
				maxChangeInCases = changeInCases;
			}
			
			xyPair = new HashMap<>();
			xyPair.put("x", (valueToday + valueYesterday) / 2);  //use average of yesterday and today as the current case number
			xyPair.put("y", changeInCases);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.30482; //need to compute this for each dataset using regression on a subset of the data (early days)
		scatterChartDataLists.add(makeExponentialLineList(regionCaseList.get(0).getTotalPositiveCases(),
				regionCaseList.get(regionCaseList.size() - 1).getTotalPositiveCases(), k));

		log.info("***** DONE MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> regionDataList) {
		log.info("***** MAKING CHANGE IN DAILY DEATHS VERSUS TOTAL DEATHS *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//Find the first object having a totalDeaths > 0 for two consecutive days
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList);		
		int valueToday;
		int valueYesterday;
		double changeInDeaths = 0;
		double maxChangeInDeaths = regionDataList.get(0).getTotalDeaths();
		dailyChgDeaths.clear();
		for(int dayIndex = startDayIndex; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			valueToday = regionDataList.get(dayIndex).getTotalDeaths();
			changeInDeaths = valueToday - valueYesterday;
			
			dailyChgDeaths.put(dayIndex, changeInDeaths);
			
			if(changeInDeaths > maxChangeInDeaths) {
				maxChangeInDeaths = changeInDeaths;
			}
			
			xyPair = new HashMap<>();
			xyPair.put("x", (valueToday + valueYesterday) / 2);  //use average of yesterday and today as the current case number
			xyPair.put("y", changeInDeaths);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.30482;  //need to compute this for region using regression on early data
		scatterChartDataLists.add(makeExponentialLineList(regionDataList.get(startDayIndex).getTotalDeaths(),
				regionDataList.get(regionDataList.size() - 1).getTotalDeaths(), k));

		log.info("***** DONE MAKING CHANGE IN DAILY DEATHS VERSUS TOTAL DEATHS *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		int valueToday;
		int valueYesterday;
		double percentChange = 0;
		dailyPctChgDeaths.clear();
		
		//Find the first object having a totalDeaths > 0 for two consecutive days
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList);
		for(int dayIndex = startDayIndex; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			valueToday = regionDataList.get(dayIndex).getTotalDeaths();
			percentChange = (valueToday - valueYesterday) * 100.0 / valueYesterday;
			
			dailyPctChgDeaths.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyPctChgDeaths, startDayIndex + MOVING_AVERAGE_SIZE, regionDataList.size()));

		log.info("***** DONE MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME *****");

		return scatterChartDataLists;
	}
	
	private <T extends CanonicalCases> int findFirstDayIndexWithPositiveDeaths(List<T> regionDataList) {
		int startDayIndex = 0;
		for(int dayIndex = startDayIndex + 1; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getTotalDeaths() > 0 && regionDataList.get(dayIndex - 1).getTotalDeaths() > 0) {
				startDayIndex = dayIndex;
				break;
			}
		}
		return startDayIndex;
	}
	
	List<Map<Object, Object>> makeExponentialLineList(int minCases, int maxCases, double k) {
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
	
	List<Map<Object, Object>> makeMovingAverageList(Map<Integer, Double> caseMap, int startDayIndex, int endDayIndex) {
		double movingAverage;
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		for(int dayIndex = startDayIndex; dayIndex < endDayIndex; dayIndex++) {
			movingAverage = 0;
			for(int d = dayIndex; d > dayIndex - MOVING_AVERAGE_SIZE; d--) {
				movingAverage += caseMap.get(d);
			}
			movingAverage /= 4.0;
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", movingAverage);
			movingAverageList.add(xyPair);
		}
		
		return movingAverageList;
	}
}
