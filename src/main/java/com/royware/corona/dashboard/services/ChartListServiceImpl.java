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
	
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> expFitList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = 0;
		for(CanonicalCases cc : regionCaseList) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", cc.getTotalPositiveCases());
			caseList.add(xyPair);
			dayIndex++;
		}
		scatterChartDataList.add(caseList);

		//TODO: Figure out how to make the exponential regression fit data and populate it here
		//For now, just make a generic exponential model: 100*exp(0.02*t)
		dayIndex = 0;
		while(dayIndex < regionCaseList.size()) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", 100 * Math.exp(0.15 * dayIndex));
			expFitList.add(xyPair);
			dayIndex++;
		}
		scatterChartDataList.add(expFitList);
		
		log.info("***** CASES VERSUS TIME *****");
//		int i = 0;
//		for(List<Map<Object, Object>> xyl : scatterChartDataList) {
//			log.info("LIST: " + i);
//			for(Map<Object, Object> map : xyl) {
//				log.info(map.get("x") + ", " + map.get("y"));
//			}
//			i++;
//		}

		return scatterChartDataList;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		log.info("***** RATE OF CHANGE OF DAILY CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = 1;
		log.info("dayIndex: " + dayIndex + ", regionCaseList.size() = " + regionCaseList.size());
		int ccToday;
		int ccYesterday;
		Map<Integer, Double> dailyPctChgCases = new HashMap<>();
		double percentChange = 0;
		for(int c = dayIndex; c < regionCaseList.size(); c++) {
			ccYesterday = regionCaseList.get(c - 1).getTotalPositiveCases();
			ccToday = regionCaseList.get(c).getTotalPositiveCases();
			percentChange = (ccToday - ccYesterday) * 100.0 / ccYesterday;
//			log.info(ccToday + ", " + ccYesterday + ", percentChange: " + percentChange);
			dailyPctChgCases.put(dayIndex, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			caseList.add(xyPair);
			dayIndex++;
		}
		scatterChartDataList.add(caseList);

		//make the MOVING AVERAGE
		double movingAverage;
		for(int day = MOVING_AVERAGE_SIZE; day <= dailyPctChgCases.size(); day++) {
			movingAverage = 0;
			for(int d = day; d > day - MOVING_AVERAGE_SIZE; d--) {
				movingAverage += dailyPctChgCases.get(d);
			}
			movingAverage /= 4.0;
			
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", movingAverage);
			movingAverageList.add(xyPair);
		}
		scatterChartDataList.add(movingAverageList);

//		int i = 0;
//		for(List<Map<Object, Object>> xyl : scatterChartDataList) {
//			log.info("LIST: " + i);
//			for(Map<Object, Object> map : xyl) {
//				log.info(map.get("x") + ", " + map.get("y"));
//			}
//			i++;
//		}
		return scatterChartDataList;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
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

}
