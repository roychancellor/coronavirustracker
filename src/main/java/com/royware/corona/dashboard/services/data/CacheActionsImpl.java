package com.royware.corona.dashboard.services.data;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.WorldData;

@Component
public class CacheActionsImpl implements CacheActions {
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceWorldImpl.class);
	
	@Autowired
	private WorldDataServiceCaller worldDataServiceCaller;
	
	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period}", fixedDelayString = "${spring.cache.refresh.period}")
	public void cacheEvictAndRepopulate() {
		log.info("About to START the evict and repopulate process at: " + LocalDateTime.now());
		
		log.info("Getting the world data from its source...if unavailable, will NOT evict the cache.");
		List<WorldData> newCacheData = getNewCacheDataIfAvailable();
		if(newCacheData.isEmpty()) {
			log.info("The world data source is NOT available. Returning to operation with previous version of cache.");
			return;
		}
		
		evictCache();
		log.info("DONE EVICTING: " + LocalDateTime.now());		
		
		populateCacheFromExistingData(CacheKeys.CACHE_KEY_WORLD.getName(), newCacheData);
		log.info("DONE REPOPULATING: " + LocalDateTime.now());
	}
	
	private List<WorldData> getNewCacheDataIfAvailable() {
		return worldDataServiceCaller.getDataFromWorldSource();
	}
	
	//The following two methods used to be annotated with @CacheEvict and @CachePut, but they didn't seem to be working properly
	//so now doing the eviction and repopulation manually with get and put operations.
	@Override
	public void evictCache() {
		log.info("In the evictCache method: " + LocalDateTime.now());
		log.info("EVICTING...");
		CacheManagerProvider.getManager().clear();
		log.info("...DONE");
	}	

	@Override
	public void populateCacheFromExistingData(String cacheKey, List<WorldData> newCacheData) {
		log.info("In the populateCacheFromExistingData method: " + LocalDateTime.now());
		CacheManagerProvider.getManager().put(cacheKey, newCacheData);
	}

	@Override
	public void populateCacheFromSource(String cacheKey) {
		log.info("In the populateCacheFromSource method: " + LocalDateTime.now());
		log.info("Getting the world data from its source...");
		CacheManagerProvider.getManager().put(cacheKey, getNewCacheDataIfAvailable());
	}
}
