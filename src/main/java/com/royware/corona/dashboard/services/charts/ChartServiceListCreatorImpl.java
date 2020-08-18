package com.royware.corona.dashboard.services.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.ChartServiceListCreator;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
 
@Service
public class ChartServiceListCreatorImpl implements ChartServiceListCreator {
	private static final Logger log = LoggerFactory.getLogger(ChartServiceListCreatorImpl.class);
	private Map<Integer, Double> dailyPctChgCases = new HashMap<>();
	private Map<Integer, Double> dailyChgCases = new HashMap<>();
	private Map<Integer, Double> dailyNewCases = new HashMap<>();
	private Map<Integer, Double> dailyDeaths = new HashMap<>();
	private Map<Integer, Double> dailyPctChgDeaths = new LinkedHashMap<>();
	private Map<Integer, Double> dailyChgDeaths = new LinkedHashMap<>();
	private Map<Integer, Double> dailyTests = new HashMap<>();
	private Map<Integer, Double> dailyRatioOfTests = new HashMap<>();
	private Map<Integer, Double> dailyHospitalizations = new HashMap<>();
	private Map<Integer, Double> cumulHospitalizations = new HashMap<>();
	
	//CASES WILL ALWAYS START WITH INDEX = 0 --> DAY = 0
	//DEATHS WILL HAVE A VARIABLE-INDEX --> DAY = 0 (DEATHS LAG CASES AND VARIES AMONG REGIONS)
	//TESTS WILL ALWAYS START WITH INDEX = 0 --> DAY = 0
	//HOSPITALIZATIONS WILL HAVE A VARIABLE-INDEX --> DAY = 0 (DATA NOT ALWAYS AVAILABLE AMONG REGIONS)
	
	////////// CASES ///////////
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAndTotalCasesVersusTimeList(List<T> regionDataList) {
		log.info("***** MAKING TOTAL AND DAILY CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//First day
		xyPair = makeXYPairWithDateStamp(0, regionDataList.get(0).getTotalPositiveCases(), regionDataList.get(0).getDateChecked().toString());
		dataList.add(xyPair);
		
		//All subsequent days
		int dayIndex = 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = makeXYPairWithDateStamp(
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
		scatterChartDataLists.add(makeMovingAverageList(dailyNewCases, MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));
		
		log.info("***** DONE MAKING TOTAL AND DAILY CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionDataList) {
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
		for(int dayIndex = startIndexForRateOfChangeOfCases; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			percentChange = (valueToday - valueYesterday) * 100.0 / valueYesterday;
			
			dailyPctChgCases.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyPctChgCases, MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));

		log.info("***** DONE MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> regionDataList) {
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
		scatterChartDataLists.add(makeMovingAverageList(dailyAccelCases, MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + 1, regionDataList.size()));

		log.info("***** DONE MAKING ACCELERATION OF DAILY CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}
	
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCasesWithExponentialLineList(List<T> regionDataList) {
		log.info("***** MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		int valueToday;
		int valueYesterday;
		double changeInCases = 0;
		double maxChangeInCases = regionDataList.get(0).getTotalPositiveCases();
		dailyChgCases.clear();
		int startIndexForDailyChangeOfCases = 1; //day 1 in list
		for(int dayIndex = startIndexForDailyChangeOfCases; dayIndex < regionDataList.size(); dayIndex++) {
			valueYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases();
			valueToday = regionDataList.get(dayIndex).getTotalPositiveCases();
			changeInCases = valueToday - valueYesterday;
			
			dailyChgCases.put(dayIndex, changeInCases);
			
			if(changeInCases > maxChangeInCases) {
				maxChangeInCases = changeInCases;
			}
			
			xyPair = new HashMap<>();
			xyPair.put("x", (valueToday + valueYesterday) / 2);  //use average of yesterday and today as the current case number
			xyPair.put("y", changeInCases);
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.30998; //need to compute this for each dataset using regression on a subset of the data (early days)
		int minCases = regionDataList.get(0).getTotalPositiveCases();
		if(minCases <= 0) {
			minCases = 1;
		}
		int maxCases = regionDataList.get(regionDataList.size() - 1).getTotalPositiveCases();
		if(maxCases <= 0) {
			maxCases = 10;
		}
		scatterChartDataLists.add(makeExponentialLineList(minCases, maxCases, k));

		log.info("***** DONE MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeCurrentTotalPositivesWithPercentOfPopulationList(List<T> regionDataList,
			Integer regionPopulation) {
		log.info("***** MAKING CURRENT TOTAL POSITIVES VERSUS TIME *****");
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
		int rollingSumPrimary = 0;
		int rollingSumSecondary = 0;
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
				
		log.info("***** DONE MAKING CURRENT TOTAL POSITIVES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	////////////// DEATHS ///////////////
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeTotalDeathsVersusTimeWithExponentialFitList(List<T> regionDataList) {
		log.info("***** MAKING TOTAL AND DAILY DEATHS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();

		log.info("Making time history of TOTAL deaths");
		log.info("Making time history of DAILY deaths");
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList);
		xyPair = makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getTotalDeaths(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		int dayIndex = startDayIndex + 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = makeXYPairWithDateStamp(
					dayIndex,
					regionDataList.get(dayIndex).getTotalDeaths(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			totalToday = regionDataList.get(dayIndex).getTotalDeaths();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;
			dailyDeaths.put(dayIndex, dailyChange * 1.0);

			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily deaths to smooth out some of the noise
		log.info("Making moving average of DAILY deaths");
		scatterChartDataLists.add(makeMovingAverageList(dailyDeaths, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));
		
		log.info("***** DONE MAKING TOTAL AND DAILY DEATHS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		dailyPctChgDeaths.clear();
		
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList) + 1;
		log.info("startDayIndex for daily deaths: " + startDayIndex);
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
		scatterChartDataLists.add(makeMovingAverageList(dailyPctChgDeaths, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));

		log.info("***** DONE MAKING RATE OF CHANGE OF DAILY DEATHS VERSUS TIME *****");

		return scatterChartDataLists;
	}
	
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfDeathsWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING ACCELERATION OF DAILY DEATHS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		Map<Integer, Double> dailyAccelDeaths = new HashMap<>();
		
		double valueToday;
		double valueYesterday;
		double valueDayBeforeYesterday;
		double accelerationOfDeaths = 0;
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList);
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
		scatterChartDataLists.add(makeMovingAverageList(dailyAccelDeaths, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + 1, regionDataList.size()));

		log.info("***** DONE MAKING ACCELERATION OF DAILY DEATHS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> regionDataList) {
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
		int dayIndex = startDayIndex + 1;
		while(dayIndex < regionDataList.size()) {
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
			xyPair.put("dateChecked", regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.29966;  //need to compute this for region using regression on early data
		int minCases = regionDataList.get(0).getTotalDeaths();
		if(minCases <= 0) {
			minCases = 1;
		}
		int maxCases = regionDataList.get(regionDataList.size() - 1).getTotalDeaths();
		if(maxCases <= 0) {
			maxCases = 10;
		}
		scatterChartDataLists.add(makeExponentialLineList(minCases, maxCases, k));

		log.info("***** DONE MAKING CHANGE IN DAILY DEATHS VERSUS TOTAL DEATHS *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeCurrentTotalDeathsWithPercentOfPopulationList(List<T> regionDataList,
			Integer regionPopulation) {
		log.info("***** MAKING CURRENT TOTAL DEATHS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		Map<Object, Object> xyPairSec;
		List<Map<Object, Object>> dataListPrimary = new ArrayList<>();
		List<Map<Object, Object>> dataListSecondary = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		//Makes a list of total current deaths for each day on a rolling basis
		int startDayIndex = findFirstDayIndexWithPositiveDeaths(regionDataList);
		Queue<Integer> dailyChangeInDeathsPrimary = new LinkedList<>();
		Queue<Integer> dailyChangeInDeathsSecondary = new LinkedList<>();
		dailyChangeInDeathsPrimary.add(regionDataList.get(startDayIndex).getTotalDeaths());
		dailyChangeInDeathsSecondary.add(regionDataList.get(startDayIndex).getTotalDeaths());
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		int rollingSumPrimary = 0;
		int rollingSumSecondary = 0;
		int dayIndex = startDayIndex = 1;
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
				
		log.info("***** DONE MAKING CURRENT TOTAL DEATHS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	////////////// TESTS ///////////////
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyTestsTotalTestsVersusTimeList(List<T> regionDataList) {
		log.info("***** MAKING TOTAL AND DAILY TESTS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		xyPair = makeXYPairWithDateStamp(0,
				regionDataList.get(0).getTotalPositiveCases() + regionDataList.get(0).getTotalNegativeCases(),
				regionDataList.get(0).getDateChecked().toString());
		dataList.add(xyPair);
		
		int dayIndex = 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getTotalPositiveCases() + regionDataList.get(dayIndex).getTotalNegativeCases(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalPositiveCases() + regionDataList.get(dayIndex - 1).getTotalNegativeCases();
			totalToday = regionDataList.get(dayIndex).getTotalPositiveCases() + regionDataList.get(dayIndex).getTotalNegativeCases();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;
			dailyTests.put(dayIndex, dailyChange * 1.0);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily quantity to smooth out some of the noise
		scatterChartDataLists.add(makeMovingAverageList(dailyTests, MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));
		
		log.info("***** DONE MAKING TOTAL AND DAILY TESTS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRatioCasesToTestsWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING RATIO OF CASES TO TESTS VERSUS TIME *****");
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
		scatterChartDataLists.add(makeMovingAverageList(dailyRatioOfTests, MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));

		log.info("***** DONE MAKING RATIO OF CASES TO TESTS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	////////////// HOSPITALIZATIONS ///////////////
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedNowWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		//Find the first object having a current hospitalizations > 0 for two consecutive days
		log.info("Making time history of CURRENT hospitalizations");
		log.info("Making time history of DAILY NEW hospitalizations");
		
		//First day with positive hospitalizations
		int startDayIndex = findFirstDayIndexWithPositiveCurrentHospitalizations(regionDataList);
		xyPair = makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getHospitalizedCurrently(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		//All remaining days
		int dayIndex = startDayIndex + 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//CURRENT
			xyPair = makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getHospitalizedCurrently(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getHospitalizedCurrently();
			totalToday = regionDataList.get(dayIndex).getHospitalizedCurrently();
			dailyChange = totalToday - totalYesterday;
			dailyHospitalizations.put(dayIndex, dailyChange * 1.0);

			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily new hospitalizations to smooth out some of the noise
		log.info("Making moving average of DAILY NEW hospitalizations");
		scatterChartDataLists.add(makeMovingAverageList(dailyHospitalizations, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));
		
		log.info("***** DONE MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedTotalWithMovingAverageList(List<T> regionDataList) {
		log.info("***** MAKING CUMULATIVE AND DAILY NEW HOSPITALIZATIONS (FROM CUMULATIVE) VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();

		log.info("Making time history of CUMULATIVE hospitalizations");
		log.info("Making time history of DAILY NEW hospitalizations (from Cumulative)");
		int startDayIndex = findFirstDayIndexWithPositiveCumulativeHospitalizations(regionDataList);
		xyPair = makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getHospitalizedCumulative(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		int dayIndex = startDayIndex + 1;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getHospitalizedCumulative(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getHospitalizedCumulative();
			totalToday = regionDataList.get(dayIndex).getHospitalizedCumulative();
			dailyChange = totalToday - totalYesterday;
			cumulHospitalizations.put(dayIndex, dailyChange * 1.0);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily new hospitalizations to smooth out some of the noise
		log.info("Making moving average of DAILY NEW hospitalizations");
		scatterChartDataLists.add(makeMovingAverageList(cumulHospitalizations, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(), regionDataList.size()));
		
		log.info("***** DONE MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS (FROM CULULATIVE) VERSUS TIME *****");

		return scatterChartDataLists;
	}

	//////////// HELPER METHODS /////////////
	private Map<Object, Object> makeXYPairWithDateStamp(int xValue, int yValue, String dateStamp) {
		Map<Object, Object> xyPair;
		xyPair = new HashMap<>();
		xyPair.put("x", xValue);
		xyPair.put("y", yValue);
		xyPair.put("dateChecked", dateStamp);
		return xyPair;
	}

	private <T extends CanonicalData> int findFirstDayIndexWithPositiveDeaths(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getTotalDeaths() > 0) {
				log.info("first day index with positive deaths: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getTotalDeaths() + " deaths");
				return dayIndex;
			}
		}
		return 0;
	}
	
	private <T extends CanonicalData> int findFirstDayIndexWithPositiveCurrentHospitalizations(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getHospitalizedCurrently() > 0) {
				log.info("first day index with positive current hospitalizations: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getHospitalizedCurrently() + " hospitalizations");
				return dayIndex;
			}
		}
		return 0;
	}
	
	private <T extends CanonicalData> int findFirstDayIndexWithPositiveCumulativeHospitalizations(List<T> regionDataList) {
		for(int dayIndex = 0; dayIndex < regionDataList.size(); dayIndex++) {
			if(regionDataList.get(dayIndex).getHospitalizedCumulative() > 0) {
				log.info("first day index with positive cumulative hospitalizations: " + dayIndex
						+ " with " + regionDataList.get(dayIndex).getHospitalizedCumulative() + " hospitalizations");
				return dayIndex;
			}
		}
		return 0;
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
	
	List<Map<Object, Object>> makeMovingAverageList(Map<Integer, Double> dataMap, int startDayIndex, int endDayIndex) {
		double movingAverage;
		int divisor = 0;
		double amountToAdd = 0;
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		log.info("Making the moving average...");
		for(int dayIndex = startDayIndex; dayIndex < endDayIndex; dayIndex++) {
			movingAverage = 0;
			for(int d = dayIndex; d > dayIndex - MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(); d--) {
				amountToAdd = dataMap.get(d);
				if(!(Double.isNaN(amountToAdd) || Double.isInfinite(amountToAdd) || (int)amountToAdd == 100)) {
					movingAverage += amountToAdd;
					divisor++;
				} else {
					log.info("Oops...amountToAdd is not a real number, it is " + amountToAdd + ", so it will NOT be in the moving average.");
				}
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
