package com.royware.corona.dashboard.services.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.CacheKeys;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.WorldData;

/**
 * Provides service methods for getting dashboard data from external sources
 */

@Component("singleCountry")
public class SingleCountryDataServiceImpl implements ExternalDataService {
	@Autowired
	@Qualifier(value = "world")
	private ExternalDataService worldDataService;
	
	private static final int MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION = 10;
	private static final int MINIMUM_TOTAL_CASES_FOR_INCLUSION = 100;
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldData> makeDataListFromExternalSource(String countryThreeLetterCode) {
		log.info("***** ABOUT TO GET DATA FOR COUNTRY " + countryThreeLetterCode + " ****");
		List<WorldData> casesInOneCountry = new ArrayList<>();
		log.info("Calling the WorldDataServiceImpl makeDataListFromExternalSource method (should be cached)");
		List<WorldData> worldCases = worldDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
		log.info("Got the world data");
		//Because the country data returns daily new cases and deaths, need to compute the totals by day
		log.info("***** ABOUT TO FILTER FOR COUNTRY " + countryThreeLetterCode + " ****");
		casesInOneCountry = worldCases
				.stream()
				.filter(wc -> {
					return wc.getRegionString().equalsIgnoreCase(countryThreeLetterCode)
							&& wc.getDailyNewCases() >= MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION;
				})
				.collect(Collectors.toList());
		
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
		log.info("***** FINISHED FILTER FOR COUNTRY " + countryThreeLetterCode + " ****");
	
		return casesInOneCountry;
	}
}
