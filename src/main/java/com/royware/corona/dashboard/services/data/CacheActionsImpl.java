package com.royware.corona.dashboard.services.data;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;

@Component
public class CacheActionsImpl implements CacheActions {
	private static final Logger log = LoggerFactory.getLogger(WorldDataServiceImpl.class);
	
	@Autowired
	@Qualifier("world")
	private WorldDataServiceCaller worldDataService;
	
	@Autowired
	@Qualifier("cacheManager")
	private CacheManager cacheManager;
	
	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period}", fixedDelayString = "${spring.cache.refresh.period}")
	public void cacheEvictAndRepopulate() {
		log.info("About to START the evict and repopulate process at: " + LocalDateTime.now());
		log.info("Before evicting cache, the contents with key {} are: {}", CacheKeys.CACHE_KEY_WORLD.getName(),
				cacheManager
				.getCache(CacheActions.CACHE_NAME)
				.get(CacheKeys.CACHE_KEY_WORLD.getName())
				.get());
		evictCache();
		log.info("DONE EVICTING: " + LocalDateTime.now());
		
		repopulateCache(CacheKeys.CACHE_KEY_WORLD.getName());
		log.info("After repopulating cache, the contents with key {} are: {}", CacheKeys.CACHE_KEY_WORLD.getName(),
				cacheManager
				.getCache(CacheActions.CACHE_NAME)
				.get(CacheKeys.CACHE_KEY_WORLD.getName())
				.get());
		log.info("DONE REPOPULATING: " + LocalDateTime.now());
	}
	
	//The following two methods used to be annotated with @CacheEvict and @CachePut, but they didn't seem to be working properly
	//so now doing the eviction and repopulation manually with get and put operations.
	@Override
	public void evictCache() {
		log.info("In the evictCache method: " + LocalDateTime.now());
		log.info("EVICTING...");
		cacheManager.getCache(CACHE_NAME).clear();
		log.info("...DONE");
	}	

	public void repopulateCache(String cacheKey) {
		log.info("In the repopulateCache method: " + LocalDateTime.now());
		cacheManager.getCache(CACHE_NAME).put(cacheKey, worldDataService.getDataFromWorldSource());
	}		
}
