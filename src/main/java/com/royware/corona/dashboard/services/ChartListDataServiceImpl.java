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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.model.WorldRecords;
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
	private static final int MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION = 20;
	private static final int MINIMUM_TOTAL_CASES_FOR_INCLUSION = 100;

	/**
	 * Gets all US data and returns it as an array of objects
	 * @return JSON array of UnitedStatesData objects
	 */
	public List<UnitedStatesCases> getAllUsData() {
		UnitedStatesCases[] usData = null;
		int tries = 0;
		do {
			try {
				log.info("***** ABOUT TO HIT ENDPOINT FOR UNITED STATES DATA *****");
				usData = restTemplate.getForObject(
						"https://covidtracking.com/api/us/daily",
						UnitedStatesCases[].class
				);
				log.info("The US data object array is:");
				for(UnitedStatesCases usd : usData) {
					log.info(usd.toString());
				}
				log.info("***** GOT THROUGH PARSING UNITED STATES DATA *****");
			} catch(RestClientException e) {
				log.info("*** ERROR CONNECTING TO U.S. DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				e.printStackTrace();
				usData = null;
				tries++;
			}
		} while(tries <= 3 && usData == null);
		
		return Arrays.asList(usData);
	}

	@Override
	public List<WorldCases> getAllWorldData() {
		WorldRecords worldData = null;
		int tries = 0;
		do {	
			try {
				log.info("***** ABOUT TO HIT ENDPOINT FOR ALL WORLD DATA *****");
				worldData = restTemplate.getForObject(
						"https://opendata.ecdc.europa.eu/covid19/casedistribution/json/",
						WorldRecords.class
				);
				
				log.info("The World data object array is:");
				for(WorldCases wc : worldData.getRecords()) {
					log.info(wc.toString());
				}
				log.info("***** GOT THROUGH PARSING ALL WORLD DATA *****");
			} catch (RestClientException e) {
				log.info("*** ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				e.printStackTrace();
				tries++;
				worldData = null;
			}
		} while (tries <= 3 && worldData == null);
		
		return Arrays.asList(worldData.getRecords());
	}

	@Override
	public List<UnitedStatesCases> getSingleUsStateData(String stateAbbreviation) {
		UnitedStatesCases[] stateData = restTemplate.getForObject(
				"https://covidtracking.com/api/states/daily?state=" + stateAbbreviation.toUpperCase(),
				UnitedStatesCases[].class
		);
		
		return Arrays.asList(stateData);
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
	public List<WorldCases> getSingleNonUsCountryData(String countryThreeLetterCode) {
		log.info("***** ABOUT TO FILTER FOR COUNTRY " + countryThreeLetterCode + " ****");
		List<WorldCases> casesInOneCountry = new ArrayList<>();
		//Because the country data returns daily new cases and deaths, need to compute the totals by day
		int positiveCases = 0;
		int negativeCases = 0;
		int totalDeaths = 0;
		casesInOneCountry = getAllWorldData()
				.stream()
				.filter(wc -> {
					return wc.getRegionAbbrev().equalsIgnoreCase(countryThreeLetterCode)
							&& wc.getDailyNewCases() >= MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION;
				})
				.collect(Collectors.toList());
		
		WorldCases wc;
		for(int i = casesInOneCountry.size() - 1; i >= 0; i--) {
			wc = casesInOneCountry.get(i);
			positiveCases += wc.getDailyNewCases();
			negativeCases += wc.getDailyNewCases();  //DELETE THIS LATER...DON'T USE NEGATIVES FOR ANYTHING
			totalDeaths += wc.getDailyNewDeaths();
			if(positiveCases >= MINIMUM_TOTAL_CASES_FOR_INCLUSION) {
				wc.setTotalPositiveCases(positiveCases);
				wc.setTotalNegativeCases(negativeCases);
				wc.setTotalDeaths(totalDeaths);
			}
		}
		
		return casesInOneCountry;
	}
}
