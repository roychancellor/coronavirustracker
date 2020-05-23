package com.royware.corona.dashboard.services.data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.CacheKeys;
import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.model.WorldData;
import com.royware.corona.dashboard.model.WorldRecords;

@Component("world")
@CacheConfig(cacheNames = {CacheActions.CACHE_NAME})
public class WorldDataServiceImpl implements ExternalDataService, CacheActions {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable
	public List<WorldData> makeDataListFromExternalSource(String cacheKey) {
		WorldRecords worldData = null;
		int tries = 0;
		do {	
			try {
				log.info("***** ABOUT TO HIT ENDPOINT FOR ALL WORLD DATA *****");
				worldData = restTemplate.getForObject(DataUrls.WORLD_DATA_URL.getName(), WorldRecords.class);				
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
	@Scheduled(initialDelay = CACHE_EVICT_PERIOD_MILLISECONDS_PROD, fixedDelay = CACHE_EVICT_PERIOD_MILLISECONDS_PROD)
	public void cacheEvictAndRepopulate() {
		log.info("About to START the evict and repopulate process at: " + LocalDateTime.now());
		evictCache();
		log.info("DONE EVICTING: " + LocalDateTime.now());
		repopulateCache();
		log.info("DONE REPOPULATING: " + LocalDateTime.now());
	}
	
	@CacheEvict(value = CACHE_NAME, allEntries = true)
	private void evictCache() {
		log.info("\tIn the evictCache method: " + LocalDateTime.now());
	}	

	@CachePut(value = CACHE_NAME)
	private List<WorldData> repopulateCache() {
		log.info("In the repopulateCache method: " + LocalDateTime.now());
		return makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}	
}
