package com.royware.corona.dashboard.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@EnableScheduling
public class SingleStateDataServiceImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private String cacheKeyForThisState;
	
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(key = "#stateAbbreviation", value = CACHE_NAME)
	public List<UnitedStatesCases> makeDataListFromExternalSource(String stateAbbreviation) {
		cacheKeyForThisState = stateAbbreviation;
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
	
	@CacheEvict(key = "#cacheKeyForThisState", cacheNames = {CACHE_NAME})
	@Scheduled(fixedDelay = CACHE_EVICT_PERIOD_MILLISECONDS)
	@Override
	public void cacheEvict() {
		log.info("CACHE FOR STATE " + cacheKeyForThisState + " EVICTED AT: " + LocalDateTime.now());
		log.info("Repopulating cache...");
		repopulateCache();
		log.info("DONE REPOPULATING CACHE FOR " + cacheKeyForThisState + " AT: " + LocalDateTime.now());
	}
	
	private void repopulateCache() {
		makeDataListFromExternalSource(cacheKeyForThisState);
	}
}
