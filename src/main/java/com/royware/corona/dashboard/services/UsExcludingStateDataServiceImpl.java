package com.royware.corona.dashboard.services;

import java.time.LocalDateTime;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.CacheKeys;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@EnableScheduling
public class UsExcludingStateDataServiceImpl implements ExternalDataService {
	@Autowired
	@Qualifier(value = "us")
	private ExternalDataService usDataService;
	
	@Autowired
	@Qualifier(value = "singleState")
	private ExternalDataService stateDataService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private String cacheKeyToEvict;
	
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(key = "#stateToExclude", value = CACHE_NAME)
	public List<UnitedStatesCases> makeDataListFromExternalSource(String stateToExclude) {
		cacheKeyToEvict = stateToExclude;
		//call getAllUsData, then call the states API and subtract out the state numbers
		log.info("***** ABOUT TO FILTER *OUT* STATE: " + stateToExclude + " ****");
		List<UnitedStatesCases> usDataExcludingState = usDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_US.toString());
		List<UnitedStatesCases> stateDataToExclude = stateDataService.makeDataListFromExternalSource(stateToExclude);
		
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

	@CacheEvict(key = "#cacheKeyToEvict", cacheNames = {CACHE_NAME})
	@Scheduled(fixedDelay = CACHE_EVICT_PERIOD_MILLISECONDS)
	@Override
	public void cacheEvict() {
		log.info("CACHE FOR EXCLUDED STATE " + cacheKeyToEvict + " EVICTED AT: " + LocalDateTime.now());
		log.info("Repopulating cache...");
		repopulateCache();
		log.info("DONE REPOPULATING CACHE FOR EXCLUDED STATE " + cacheKeyToEvict);
	}
	
	private void repopulateCache() {
		makeDataListFromExternalSource(cacheKeyToEvict);
	}
}
