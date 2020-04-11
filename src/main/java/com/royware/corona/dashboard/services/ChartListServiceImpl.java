package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		log.info("***** MAKING CASES VERSUS TIME *****");
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
		
		log.info("***** DONE MAKING CASES VERSUS TIME *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		log.info("***** MAKING RATE OF CHANGE OF DAILY CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		int ccToday;
		int ccYesterday;
		double percentChange = 0;
		dailyPctChgCases.clear();
		for(int day = 1; day < regionCaseList.size(); day++) {
			ccYesterday = regionCaseList.get(day - 1).getTotalPositiveCases();
			ccToday = regionCaseList.get(day).getTotalPositiveCases();
			percentChange = (ccToday - ccYesterday) * 100.0 / ccYesterday;
			dailyPctChgCases.put(day, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", percentChange);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyPctChgCases, MOVING_AVERAGE_SIZE));

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
		double pccToday;
		double pccYesterday;
		double percentChange = 0;
		for(int day = 2; day < regionCaseList.size(); day++) {
			pccYesterday = dailyPctChgCases.get(day - 1);
			pccToday = dailyPctChgCases.get(day);
			percentChange = (pccToday - pccYesterday) * 100.0 / pccYesterday;
			dailyAccelCases.put(day, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", percentChange);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE
		scatterChartDataLists.add(makeMovingAverageList(dailyAccelCases, MOVING_AVERAGE_SIZE + 1));

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
		int ccToday;
		int ccYesterday;
		double changeInCases = 0;
		dailyChgCases.clear();
		for(int day = 1; day < regionCaseList.size(); day++) {
			ccYesterday = regionCaseList.get(day - 1).getTotalPositiveCases();
			ccToday = regionCaseList.get(day).getTotalPositiveCases();
			changeInCases = ccToday - ccYesterday;
			dailyChgCases.put(day, changeInCases);
			
			xyPair = new HashMap<>();
			xyPair.put("x", ccToday);
			xyPair.put("y", changeInCases);
			dataList.add(xyPair);
		}
		scatterChartDataLists.add(dataList);

		//make the EXPONENTIAL (will show as straight line on log-log graph)
		double k = 0.3;
		scatterChartDataLists.add(makeExponentialLineList(dailyChgCases, k));

		log.info("***** DONE MAKING CHANGE IN DAILY CASES VERSUS TOTAL CASES *****");

		return scatterChartDataLists;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsswithExponentialLineList(List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	List<Map<Object, Object>> makeExponentialLineList(Map<Integer, Double> caseList, double k) {
		List<Map<Object, Object>> expLineList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		xyPair = new HashMap<>();
		xyPair.put("x", caseList.get(1));
		xyPair.put("y", k * caseList.get(1));
		expLineList.add(xyPair);
		xyPair = new HashMap<>();
		xyPair.put("x", caseList.get(caseList.size()));
		xyPair.put("y", k * caseList.get(caseList.size()));
		expLineList.add(xyPair);
		
		return expLineList;
	}
	
	List<Map<Object, Object>> makeMovingAverageList(Map<Integer, Double> caseList, int startDay) {
		double movingAverage;
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		Map<Object, Object> xyPair;
		
		for(int day = startDay; day <= caseList.size(); day++) {
			movingAverage = 0;
			for(int d = day; d > day - MOVING_AVERAGE_SIZE; d--) {
				movingAverage += caseList.get(d);
			}
			movingAverage /= 4.0;
			
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", movingAverage);
			movingAverageList.add(xyPair);
		}
		
		return movingAverageList;
	}
}
