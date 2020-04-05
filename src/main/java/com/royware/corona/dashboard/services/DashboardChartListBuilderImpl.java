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
import com.royware.corona.dashboard.interfaces.DashboardChartListBuilder;
import com.royware.corona.dashboard.interfaces.DashboardDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;
 
@Service
public class DashboardChartListBuilderImpl implements DashboardChartListBuilder {
	@Autowired
	DashboardDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
 
	public List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList() {
		UnitedStatesCases[] usData = dataService.getAllUsData();
		
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> xyList1 = new ArrayList<>();
		List<Map<Object, Object>> xyList2 = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int day = usData.length;
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", usd.getTotalPositiveCases());
			xyList1.add(xyPair);
			day--;
		}
		scatterChartDataList.add(xyList1);

		day = usData.length;
		for(UnitedStatesCases usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", day);
			xyPair.put("y", usd.getTotalPositiveCases() * 2);
			xyList2.add(xyPair);
			day--;
		}
		scatterChartDataList.add(xyList2);
		
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
		// TODO Auto-generated method stub
		return null;
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
