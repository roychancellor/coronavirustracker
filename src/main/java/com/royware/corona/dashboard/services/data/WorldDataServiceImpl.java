package com.royware.corona.dashboard.services.data;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.WorldData;
import com.royware.corona.dashboard.model.data.WorldRecords;

@Component("world")
public class WorldDataServiceImpl implements ExternalDataService, WorldDataServiceCaller {
	@Autowired
	private RestTemplate restTemplate;
		
	private static final Logger log = LoggerFactory.getLogger(WorldDataServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = CacheActions.CACHE_NAME)
	public List<WorldData> makeDataListFromExternalSource(String cacheKey) {
		log.info("Calling getDataFromWorldSource (should return data from cache).");
		return getDataFromWorldSource(cacheKey);
	}

	@Override
	public List<WorldData> getDataFromWorldSource(String cacheKey) {
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
