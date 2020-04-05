package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;

import org.omg.CORBA.WCharSeqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.interfaces.DashboardDataService;
import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Service
public class DashboardDataServiceImpl implements DashboardDataService {
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
	public UnitedStatesCases[] getAllUsData() {
		UnitedStatesCases[] usData = restTemplate.getForObject("https://covidtracking.com/api/us/daily", UnitedStatesCases[].class);
		log.info("The US data object array is:");
		for(UnitedStatesCases usd : usData) {
			log.info(usd.toString());
		}
		
		return usData;
	}

	@Override
	public UnitedStatesCases[] getAllUsDataExcludingState(String stateAbbreviation) {
		//call getAllUsData, then call the states API and subtract out the state numbers
		UnitedStatesCases[] usDataExcludingState = getAllUsData();
		UnitedStatesCases[] stateDataToExclude = getSingleUsStateData(stateAbbreviation);
		
		for(int i = 0; i < usDataExcludingState.length; i++) {
			usDataExcludingState[i].setTotalPositiveCases(
				usDataExcludingState[i].getTotalPositiveCases() - stateDataToExclude[i].getTotalPositiveCases()
			);
			usDataExcludingState[i].setTotalNegativeCases(
				usDataExcludingState[i].getTotalNegativeCases() - stateDataToExclude[i].getTotalNegativeCases()
			);
			usDataExcludingState[i].setTotalDeaths(
				usDataExcludingState[i].getTotalDeaths() - stateDataToExclude[i].getTotalDeaths()
			);
		}
		
		return usDataExcludingState;
	}

	@Override
	public UnitedStatesCases[] getSingleUsStateData(String stateAbbreviation) {
		UnitedStatesCases[] stateData = restTemplate.getForObject(
			"https://covidtracking.com/api/states/daily?state=" + stateAbbreviation,
			UnitedStatesCases[].class
		);
		log.info("The US data object array is:");
		for(UnitedStatesCases state : stateData) {
			log.info(state.toString());
		}
		
		return stateData;
	}

	@Override
	public List<WorldCases> getSingleNonUsCountryData(String countryName) {
		WorldCases[] countryData = restTemplate.getForObject(
			"https://opendata.ecdc.europa.eu/covid19/casedistribution/json/", WorldCases[].class
		);
		
		List<WorldCases> countryCases = new ArrayList<>();
		countryCases = Arrays.asList(countryData)
				.stream()
				.filter(x -> x.getCountryAbbrev().equalsIgnoreCase(countryName))
				.collect(Collectors.toList());
		
		return countryCases;
	}
}
