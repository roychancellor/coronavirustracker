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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.CacheKeys;
import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;

@EnableScheduling
public class UsDataServiceImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private String cacheKeyToEvict = CacheKeys.CACHE_KEY_US.toString();

	/**
	 * Gets all US data and returns it as an array of objects
	 * @return JSON array of UnitedStatesData objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(key = "#cacheKey", value = CACHE_NAME)
	public List<UnitedStatesCases> makeDataListFromExternalSource(String cacheKey) {
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

	@CacheEvict(key = "#cacheKeyToEvict", cacheNames = {CACHE_NAME})
	@Scheduled(fixedDelay = CACHE_EVICT_PERIOD_MILLISECONDS)
	@Override
	public void cacheEvict() {
		log.info("US DATA CACHE WITH KEY " + cacheKeyToEvict + " EVICTED AT: " + LocalDateTime.now());
		log.info("Repopulating cache...");
		repopulateCache();
		log.info("DONE REPOPULATING US CACHE AT: " + LocalDateTime.now());
	}
	
	private void repopulateCache() {
		makeDataListFromExternalSource(CacheKeys.CACHE_KEY_US.toString());
	}
}
