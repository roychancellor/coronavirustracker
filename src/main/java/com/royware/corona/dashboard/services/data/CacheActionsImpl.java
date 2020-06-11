package com.royware.corona.dashboard.services.data;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;

@Component
public class CacheActionsImpl implements CacheActions {
	private static final Logger log = LoggerFactory.getLogger(WorldDataServiceImpl.class);
	
	@Autowired
	private WorldDataServiceCaller worldDataServiceCaller;
	
	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period}", fixedDelayString = "${spring.cache.refresh.period}")
	public void cacheEvictAndRepopulate() {
		log.info("About to START the evict and repopulate process at: " + LocalDateTime.now());
		evictCache();
		log.info("DONE EVICTING: " + LocalDateTime.now());		
		populateCache(CacheKeys.CACHE_KEY_WORLD.getName());
		log.info("DONE REPOPULATING: " + LocalDateTime.now());
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
	public void populateCache(String cacheKey) {
		log.info("In the populateCache method: " + LocalDateTime.now());
		CacheManagerProvider.getManager().put(cacheKey, worldDataServiceCaller.getDataFromWorldSource());
	}
}
