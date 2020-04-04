package com.royware.corona.dashboard.services;

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
 * Provides service methods for getting dashboard data from external sources
 */
@Service
public class DashboardServiceImpl implements DashboardService {
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
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
}
