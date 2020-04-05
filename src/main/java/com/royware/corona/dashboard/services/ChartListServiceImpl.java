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
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.DataListBean;
 
@Service
public class ChartListServiceImpl implements ChartListService {
	@Autowired
	ChartListDataService dataService;
	
	@Autowired
	DataListBean dataListBean;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	public List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList() {
		//Get all the U.S. data
		List<UnitedStatesCases> usData = dataListBean.getUsData();
		
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> expFitList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = usData.size();
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", usd.getTotalPositiveCases());
			caseList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(caseList);

		//TODO: Figure out how to make the exponential regression fit data and populate it here
		//For now, just duplicate the U.S. data with a multiplier of 2
		dayIndex = usData.size();
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", usd.getTotalPositiveCases() * 2);
			expFitList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(expFitList);
		
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
	public List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList() {
		//Get all the U.S. data
		List<UnitedStatesCases> usData = dataListBean.getUsData();
		
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> caseList = new ArrayList<>();
		List<Map<Object, Object>> expFitList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int dayIndex = usData.size();
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", usd.getTotalPositiveCases());
			caseList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(caseList);

		//TODO: Figure out how to make the exponential regression fit data and populate it here
		//For now, just duplicate the U.S. data with a multiplier of 2
		dayIndex = usData.size();
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", dayIndex);
			xyPair.put("y", usd.getTotalPositiveCases() * 2);
			expFitList.add(xyPair);
			dayIndex--;
		}
		scatterChartDataList.add(expFitList);

		return scatterChartDataList;
	}

	@Override
	public List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsswithExponentialLineList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList() {
		// TODO Auto-generated method stub
		return null;
	}

}
