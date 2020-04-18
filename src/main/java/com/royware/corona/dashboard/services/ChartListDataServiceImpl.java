package com.royware.corona.dashboard.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.CacheKeys;
import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.model.WorldRecords;
import com.royware.corona.dashboard.model.UnitedStatesCases;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Service
@EnableScheduling
public class ChartListDataServiceImpl implements ChartListDataService {
//Leave these commented out for troubleshooting later. Just use normal object creation by constructor for now.
//	@Autowired
//	RestTemplate restTemplate;
//	
//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}

	private RestTemplate restTemplate;
	private static final int MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION = 10;
	private static final int MINIMUM_TOTAL_CASES_FOR_INCLUSION = 100;
	private static final long CACHE_EVICT_PERIOD_MILLISECONDS = 3 * 60 * 60 * 1000;  //every 3 hours
	private static final String CACHE_NAME = "dataCache";
	private static final int US_CUTOFF_DATE = 20200304;

	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	
	public ChartListDataServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * Gets all US data and returns it as an array of objects
	 * @return JSON array of UnitedStatesData objects
	 */
	@Override
	@Cacheable(key = "#cacheKey", value = CACHE_NAME)
	public List<UnitedStatesCases> getAllUsData(String cacheKey) {
		UnitedStatesCases[] usDataArray = null;
		int tries = 0;
		do {
			try {
				log.info("***** ABOUT TO HIT ENDPOINT FOR UNITED STATES DATA *****");
				usDataArray = restTemplate.getForObject(DataUrls.US_DATA_URL.toString(), UnitedStatesCases[].class);
				log.info("***** GOT THROUGH PARSING UNITED STATES DATA *****");
			} catch(RestClientException e) {
				log.info("*** ERROR CONNECTING TO U.S. DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				e.printStackTrace();
				usDataArray = null;
				tries++;
			}
		} while(tries <= 3 && usDataArray == null);
		
		List<UnitedStatesCases> usDataList = new ArrayList<>(Arrays.asList(usDataArray));
		Collections.reverse(usDataList);
		usDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDate() < US_CUTOFF_DATE));
		
		log.info("***** FINISHED HITTING ENDPOINT FOR UNITED STATES DATA *****");
		return usDataList;
	}

	@Override
	@Cacheable(key = "#cacheKey", value = CACHE_NAME)
	public List<WorldCases> getAllWorldData(String cacheKey) {
		WorldRecords worldData = null;
		int tries = 0;
		do {	
			try {
				log.info("***** ABOUT TO HIT ENDPOINT FOR ALL WORLD DATA *****");
				worldData = restTemplate.getForObject(DataUrls.WORLD_DATA_URL.toString(), WorldRecords.class);				
				log.info("***** GOT THROUGH PARSING ALL WORLD DATA *****");
			} catch (RestClientException e) {
				log.info("*** ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				worldData = null;
			}
		} while (tries <= 3 && worldData == null);
		
		log.info("***** FINISHED HITTING ENDPOINT FOR ALL WORLD DATA *****");
		return Arrays.asList(worldData.getRecords());
	}

	@Override
	@Cacheable(key = "#stateAbbreviation", value = CACHE_NAME)
	public List<UnitedStatesCases> getSingleUsStateData(String stateAbbreviation) {
		log.info("***** ABOUT TO GET STATE: " + stateAbbreviation + " ****");
		UnitedStatesCases[] stateDataArray = restTemplate.getForObject(
				DataUrls.STATE_DATA_URL.toString() + stateAbbreviation.toUpperCase(),
				UnitedStatesCases[].class
		);
		List<UnitedStatesCases> stateDataList = new ArrayList<>(Arrays.asList(stateDataArray));
		Collections.reverse(stateDataList);
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDate() < US_CUTOFF_DATE));
		
		for(UnitedStatesCases usc:stateDataList) {
			log.info(usc.toString());
		}
		
		log.info("***** FINISHED GETTING STATE: " + stateAbbreviation + " ****");
		
		return stateDataList;
	}

	@Override
	public List<UnitedStatesCases> getAllUsDataExcludingState(String stateToExclude) {
		//call getAllUsData, then call the states API and subtract out the state numbers
		log.info("***** ABOUT TO FILTER *OUT* STATE: " + stateToExclude + " ****");
		List<UnitedStatesCases> usDataExcludingState = getAllUsData(CacheKeys.CACHE_KEY_US.toString());
		List<UnitedStatesCases> stateDataToExclude = getSingleUsStateData(stateToExclude);
		
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
	@Cacheable(key = "#countryThreeLetterCode", value = CACHE_NAME)
	public List<WorldCases> getSingleNonUsCountryData(String countryThreeLetterCode) {
		log.info("***** ABOUT TO FILTER FOR COUNTRY " + countryThreeLetterCode + " ****");
		List<WorldCases> casesInOneCountry = new ArrayList<>();
		List<WorldCases> worldCases = getAllWorldData(CacheKeys.CACHE_KEY_WORLD.toString());
		//Because the country data returns daily new cases and deaths, need to compute the totals by day
		casesInOneCountry = worldCases
				.stream()
				.filter(wc -> {
					return wc.getRegionAbbrev().equalsIgnoreCase(countryThreeLetterCode)
							&& wc.getDailyNewCases() >= MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION;
				})
				.collect(Collectors.toList());
		
		Collections.reverse(casesInOneCountry);
		
		WorldCases wc;
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
	
	@CacheEvict(allEntries = true, cacheNames = {CACHE_NAME})
	@Scheduled(fixedDelay = CACHE_EVICT_PERIOD_MILLISECONDS)
	@Override
	public void cacheEvict() {
		log.info("CACHES EVICTED AT: " + LocalDateTime.now());
		log.info("Repopulating caches...");
		repopulateCaches();
	}
	
	private void repopulateCaches() {
		getAllUsData(CacheKeys.CACHE_KEY_US.toString());
		getAllWorldData(CacheKeys.CACHE_KEY_WORLD.toString());
		log.info("DONE REPOPULATING CACHES");
	}
}
