package com.royware.corona.dashboard.services.data.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.model.data.world.WorldData;

/**
 * Provides service methods for getting dashboard data from external sources
 */

@Component("singleCountry")
public class ExternalDataServiceSingleCountryImpl implements ExternalDataService {
	@Autowired
	@Qualifier(value = "world")
	private ExternalDataService worldDataService;
	
	private static final int MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION = 10;
	private static final int MINIMUM_TOTAL_CASES_FOR_INCLUSION = 100;
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceSingleCountryImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldData> makeDataListFromExternalSource(String countryThreeLetterCode) {
		log.info("***** ABOUT TO GET DATA FOR COUNTRY " + countryThreeLetterCode + " ****");
		List<WorldData> casesInOneCountry = new ArrayList<>();
		log.debug("Calling the WorldDataServiceImpl makeDataListFromExternalSource method (should be cached)");
		log.debug("In SingleCountryDataServiceImpl class: worldDataService hashcode: " + this.hashCode());
		List<WorldData> worldCases = worldDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
		log.debug("Got the world data list and its size is: " + worldCases.size());
		//Because the country data returns daily new cases and deaths, need to compute the totals by day
		log.debug("***** ABOUT TO FILTER FOR COUNTRY " + countryThreeLetterCode + " ****");
		casesInOneCountry = worldCases
				.stream()
				.filter(wc -> {
					if(wc.getRegionString() == null || wc.getDailyNewCases() < 0) {
						log.error("In the .filter, region string is null or daily new cases < 0");
						return false;
					}
					return wc.getRegionString().equalsIgnoreCase(countryThreeLetterCode)
							&& wc.getDailyNewCases() >= MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION;
				})
				.collect(Collectors.toList());
		
		//Euro CDC data comes in reverse chronological order, so need to reverse the list so the data is oldest to newest
		//Our World In Data comes in oldest to newest order, so don't need to reverse it, but
		//because of this statement, had to reverse the list before getting here so it gets re-reversed to correct order
		//TODO: Fix this so that only the Euro CDC data gets reversed to avoid double-reversing a list
		Collections.reverse(casesInOneCountry);
		
		WorldData wc;
		int positiveCases = 0;
		int negativeCases = 0;
		int totalDeaths = 0;
		for(int i = 0; i < casesInOneCountry.size(); i++) {
			wc = casesInOneCountry.get(i);
			positiveCases += wc.getDailyNewCases();
			totalDeaths += wc.getDailyNewDeaths();
			if(positiveCases >= MINIMUM_TOTAL_CASES_FOR_INCLUSION) {
				wc.setTotalPositiveCases(positiveCases);
				wc.setTotalNegativeCases(negativeCases);
				wc.setTotalDeaths(totalDeaths);
			} else {
				wc.setTotalPositiveCases(wc.getDailyNewCases());
				wc.setTotalDeaths(wc.getDailyNewDeaths());
			}
		}
		log.info("***** FINISHED FILTER FOR COUNTRY " + countryThreeLetterCode + " (size of list: " + casesInOneCountry.size() + ") ****");
	
		return casesInOneCountry;
	}
}
