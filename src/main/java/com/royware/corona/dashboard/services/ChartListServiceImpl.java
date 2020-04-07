package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.interfaces.ChartListService;
import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.DataListBean;
 
@Service
public class ChartListServiceImpl implements ChartListService {
	@Autowired
	ChartListDataService dataService;
	
	@Autowired
	DataListBean dataListBean;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> expFitList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = regionCaseList.size();
		for(CanonicalCases cc : regionCaseList) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", cc.getTotalPositiveCases());
			caseList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(caseList);

		//TODO: Figure out how to make the exponential regression fit data and populate it here
		//For now, just make a generic exponential model: 100*exp(0.02*t)
		dayIndex = regionCaseList.size();
		while(dayIndex >= 0) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", 100 * Math.exp(0.15 * dayIndex));
			expFitList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(expFitList);
		
		log.info("***** CASES VERSUS TIME *****");
		int i = 0;
		for(List<Map<Object, Object>> xyl : scatterChartDataList) {
			log.info("LIST: " + i);
			for(Map<Object, Object> map : xyl) {
				log.info(map.get("x") + ", " + map.get("y"));
			}
			i++;
		}

		return scatterChartDataList;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		log.info("***** RATE OF CHANGE OF CASES VERSUS TIME *****");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> movingAverageList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = regionCaseList.size();
		log.info("dayIndex: " + dayIndex + ", regionCaseList.size() = " + regionCaseList.size());
		CanonicalCases ccToday;
		CanonicalCases ccYesterday;
		Map<Integer, Double> dailyPctChgCases = new HashMap<>();
		double percentChange = 0;
		for(int c = regionCaseList.size() - 2; c >= 0; c--) {
			ccYesterday = regionCaseList.get(c + 1);  //list is in reverse time order
			ccToday = regionCaseList.get(c);
			percentChange = (ccToday.getTotalPositiveCases() - ccYesterday.getTotalPositiveCases())
					* 100.0
					/ ccYesterday.getTotalPositiveCases();
			log.info(ccToday.getTotalPositiveCases() + ", " + ccYesterday.getTotalPositiveCases() + ", percentChange: " + percentChange);
			dailyPctChgCases.put(dayIndex - 1, percentChange);
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", percentChange);
			caseList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(caseList);

		//TODO: Figure out how to make the MOVING AVERAGE and populate it here
		dayIndex = regionCaseList.size();
		double movingAverage = 0;
		for(int c = regionCaseList.size() - 1; c >= 4; c--) {
			movingAverage = (
					dailyPctChgCases.get(dayIndex - 1) +
					dailyPctChgCases.get(dayIndex - 2) +
					dailyPctChgCases.get(dayIndex - 3) +
					dailyPctChgCases.get(dayIndex - 4)
			) / 4.0;
			
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", movingAverage);
			movingAverageList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(movingAverageList);

		int i = 0;
		for(List<Map<Object, Object>> xyl : scatterChartDataList) {
			log.info("LIST: " + i);
			for(Map<Object, Object> map : xyl) {
				log.info(map.get("x") + ", " + map.get("y"));
			}
			i++;
		}
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
