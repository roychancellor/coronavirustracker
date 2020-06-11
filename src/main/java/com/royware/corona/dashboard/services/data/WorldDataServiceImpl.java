package com.royware.corona.dashboard.services.data;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.WorldData;
import com.royware.corona.dashboard.model.data.WorldRecords;

public class WorldDataServiceImpl implements ExternalDataService, WorldDataServiceCaller {
	@Autowired
	private RestTemplate restTemplate;
		
	private ConcurrentMapCache cacheManager;
	private static final Logger log = LoggerFactory.getLogger(WorldDataServiceImpl.class);

	//Pull data directly from the cache always
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldData> makeDataListFromExternalSource(String cacheKey) {
		cacheManager = CacheManagerProvider.getManager();
		List<WorldData> worldData = (List<WorldData>)cacheManager.get(cacheKey).get();
		if(worldData == null) {
			log.info("Getting the world data from its source, NOT the cache.");
			worldData = getDataFromWorldSource();
			cacheManager.put(cacheKey, worldData);
		}
		log.info("Returning the cached version of the world data.");
		return worldData;
	}

	//This only gets called when the cache is initialized and when it is evicted/refreshed
	@Override
	public List<WorldData> getDataFromWorldSource() {
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
}
