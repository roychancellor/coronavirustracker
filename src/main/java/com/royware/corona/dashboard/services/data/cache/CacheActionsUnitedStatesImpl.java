package com.royware.corona.dashboard.services.data.cache;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.ICacheActions;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;
import com.royware.corona.dashboard.services.data.world.ExternalDataServiceWorldImpl;

@Component("ca_us")
public class CacheActionsUnitedStatesImpl implements ICacheActions {
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceWorldImpl.class);
	private static final String CACHE_KEY = CacheKeys.CACHE_KEY_US.getName();
	
	@Autowired
	@Qualifier(value = "us")
	private ExternalDataService usaDataService;
		
	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period.usa}", fixedDelayString = "${spring.cache.refresh.period.usa}")
	public void cacheEvictAndRepopulate() {
		log.info("USA Cache: About to START the evict and repopulate process at: " + LocalDateTime.now());
		
		evictCache();
		log.info("USA: DONE EVICTING: " + LocalDateTime.now());		
		
		log.info("Getting the United States data from its source. If unavailable, will NOT evict the cache.");
		List<UnitedStatesData> newCacheData = getNewCacheDataFromSource();
		if(newCacheData == null || newCacheData.isEmpty()) {
			log.error("The USA data source is NOT available. Returning to operation with previous version of cache.");
			return;
		}
		
		populateCacheFromDataList(CACHE_KEY, newCacheData);
		log.info("USA: DONE REPOPULATING: " + LocalDateTime.now());
	}
	
	//The following two methods used to be annotated with @CacheEvict and @CachePut, but they didn't seem to be working properly
	//so now doing the eviction and repopulation manually with get and put operations.
	@Override
	public void evictCache() {
		log.debug("In the evictCache method: " + LocalDateTime.now());
		log.debug("EVICTING...");
		CacheManagerProvider.getManager().put(CACHE_KEY, null);
		log.debug("...DONE");
	}	

	@Override
	public <T extends CanonicalCaseDeathData> void populateCacheFromDataList(String cacheKey, List<T> newCacheData) {
		log.debug("In the populateCacheFromExistingData method: " + LocalDateTime.now());
		putDataIntoCache(cacheKey, newCacheData);
	}

	@Override
	public void populateCacheFromSource(String cacheKey) {
		log.debug("In the populateCacheFromSource method: " + LocalDateTime.now());
		log.debug("Getting the United States data from its source...");
		putDataIntoCache(cacheKey, getNewCacheDataFromSource());
	}
	
	private List<UnitedStatesData> getNewCacheDataFromSource() {
		return usaDataService.makeDataListFromExternalSource(CACHE_KEY);
	}

	private <T extends CanonicalCaseDeathData> void putDataIntoCache(String cacheKey, List<T> newCacheData) {
		CacheManagerProvider.getManager().put(cacheKey, newCacheData);
	}
}
