package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.interfaces.DashboardService;
import com.royware.corona.dashboard.model.UnitedStatesData;

/**
 * Provides service methods for all dashboard operations in the main controller
 */
@Service
public class DashboardServiceImpl implements DashboardService {
	@Autowired
	CanvasjsChartDaoImpl chartDao;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	public DashboardServiceImpl() {
//	}
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	/**
	 * Gets all US data and returns it as an array of objects
	 * @return JSON array of UnitedStatesData objects
	 */
	public UnitedStatesData[] getAllUsData() {
		UnitedStatesData[] usData = restTemplate.getForObject("https://covidtracking.com/api/us/daily", UnitedStatesData[].class);
		log.info("The US data object array is:");
		for(UnitedStatesData usd : usData) {
			log.info(usd.toString());
		}
		
		return usData;
	}
	
	public List<List<Map<Object, Object>>> makeXYScatterChartDataforUSPositiveCases(UnitedStatesData[] usData) {
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> xyList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataList = new ArrayList<>();
		int startDate = usData[usData.length - 1].getDate();
		for(UnitedStatesData usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", usd.getDate() - startDate);
			xyPair.put("y", usd.getPositive());
			xyList.add(xyPair);
		}
		scatterChartDataList.add(xyList);

		for(UnitedStatesData usd : usData) {
			xyPair = new HashMap<>();
			xyPair.put("x", usd.getDate() - startDate);
			xyPair.put("y", usd.getPositive() * 1.05);
			xyList.add(xyPair);
		}
		scatterChartDataList.add(xyList);

		return scatterChartDataList;
	}

}
