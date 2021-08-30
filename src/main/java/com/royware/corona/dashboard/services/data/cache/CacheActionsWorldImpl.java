package com.royware.corona.dashboard.services.data.cache;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ICacheActions;
import com.royware.corona.dashboard.interfaces.data.IWorldDataServiceCaller;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.data.world.WorldData;
import com.royware.corona.dashboard.services.data.source.connections.world.ExternalDataListGetterWorld;

@Component("ca_world")
public class CacheActionsWorldImpl implements ICacheActions {
	private static final Logger log = LoggerFactory.getLogger(ExternalDataListGetterWorld.class);
	private static final String CACHE_KEY = CacheKeys.CACHE_KEY_WORLD.getName();
	
	@Autowired
	private IWorldDataServiceCaller worldDataServiceCaller;
	
	@SuppressWarnings("unused")
	private boolean cleanData;
	
	@Override
	public void setCleanData(boolean cleanData) {
		this.cleanData = cleanData;
	}

	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period.world}", fixedDelayString = "${spring.cache.refresh.period.world}")
	public void cacheEvictAndRepopulate() {
		log.info("WORLD: About to START the evict and repopulate process at: " + LocalDateTime.now());
		
		log.info("Getting the world data from its source...if unavailable, will NOT evict the cache.");
		List<WorldData> newCacheData = getNewCacheDataIfAvailable();
		if(newCacheData == null || newCacheData.isEmpty()) {
			log.error("The world data source is NOT available. Returning to operation with previous version of cache.");
			return;
		}
		
		evictCache();
		log.info("WORLD: DONE EVICTING: " + LocalDateTime.now());		
		
		populateCacheFromDataList(CACHE_KEY, newCacheData);
		log.info("WORLD: DONE REPOPULATING: " + LocalDateTime.now());
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
	public <T extends ICanonicalCaseDeathData> void populateCacheFromDataList(String cacheKey, List<T> newCacheData) {
		log.debug("In the populateCacheFromExistingData method: " + LocalDateTime.now());
		CacheManagerProvider.getManager().put(cacheKey, newCacheData);
	}

	@Override
	public void populateCacheFromSource(String cacheKey) {
		log.debug("In the populateCacheFromSource method: " + LocalDateTime.now());
		log.debug("Getting the world data from its source...");
		CacheManagerProvider.getManager().put(cacheKey, getNewCacheDataIfAvailable());
	}
	
	private List<WorldData> getNewCacheDataIfAvailable() {
		return worldDataServiceCaller.getDataFromWorldSource();
	}
}
