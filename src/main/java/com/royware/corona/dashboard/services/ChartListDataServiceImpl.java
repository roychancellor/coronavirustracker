package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.model.UnitedStatesCases;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Service
public class ChartListDataServiceImpl implements ChartListDataService {
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
	public List<UnitedStatesCases> getAllUsData() {
		UnitedStatesCases[] usData = restTemplate.getForObject("https://covidtracking.com/api/us/daily", UnitedStatesCases[].class);
		log.info("The US data object array is:");
		for(UnitedStatesCases usd : usData) {
			log.info(usd.toString());
		}
		
		return Arrays.asList(usData);
	}

	@Override
	public List<UnitedStatesCases> getAllUsDataExcludingState(String stateAbbreviation) {
		//call getAllUsData, then call the states API and subtract out the state numbers
		List<UnitedStatesCases> usDataExcludingState = getAllUsData();
		List<UnitedStatesCases> stateDataToExclude = getSingleUsStateData(stateAbbreviation);
		
		for(int i = 0; i < usDataExcludingState.size(); i++) {
			usDataExcludingState.get(i).setTotalPositiveCases(
				usDataExcludingState.get(i).getTotalPositiveCases() - stateDataToExclude.get(i).getTotalPositiveCases()
			);
			usDataExcludingState.get(i).setTotalNegativeCases(
				usDataExcludingState.get(i).getTotalNegativeCases() - stateDataToExclude.get(i).getTotalNegativeCases()
			);
			usDataExcludingState.get(i).setTotalDeaths(
				usDataExcludingState.get(i).getTotalDeaths() - stateDataToExclude.get(i).getTotalDeaths()
			);
		}
		
		return usDataExcludingState;
	}

	@Override
	public List<UnitedStatesCases> getSingleUsStateData(String stateAbbreviation) {
		UnitedStatesCases[] stateData = restTemplate.getForObject(
			"https://covidtracking.com/api/states/daily?state=" + stateAbbreviation,
			UnitedStatesCases[].class
		);
		log.info("The US data object array is:");
		for(UnitedStatesCases state : stateData) {
			log.info(state.toString());
		}
		
		return Arrays.asList(stateData);
	}

	@Override
	public List<WorldCases> getSingleNonUsCountryData(String countryThreeLetterCode) {
		WorldCases[] countryData = restTemplate.getForObject(
			"https://opendata.ecdc.europa.eu/covid19/casedistribution/json/", WorldCases[].class
		);
		
		List<WorldCases> countryCases = new ArrayList<>();
		countryCases = Arrays.asList(countryData)
				.stream()
				.filter(x -> x.getCountryThreeLetterCode().equalsIgnoreCase(countryThreeLetterCode))
				.collect(Collectors.toList());
		
		return countryCases;
	}
}
