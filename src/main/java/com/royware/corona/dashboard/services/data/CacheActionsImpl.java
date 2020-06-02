package com.royware.corona.dashboard.services.data;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.CacheActions;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.WorldData;

@Component
public class CacheActionsImpl implements CacheActions {
	private static final Logger log = LoggerFactory.getLogger(WorldDataServiceImpl.class);
	
	@Autowired
	@Qualifier("world")
	WorldDataServiceCaller worldDataService;
	
	@Override
	@Scheduled(initialDelayString = "${spring.cache.refresh.period}", fixedDelayString = "${spring.cache.refresh.period}")
	public void cacheEvictAndRepopulate() {
		log.info("About to START the evict and repopulate process at: " + LocalDateTime.now());
		evictCache();
		log.info("DONE EVICTING: " + LocalDateTime.now());
		repopulateCache();
		log.info("DONE REPOPULATING: " + LocalDateTime.now());
	}
	
	@CacheEvict(value = CACHE_NAME, allEntries = true)
	@Override
	public void evictCache() {
		log.info("\tIn the evictCache method: " + LocalDateTime.now());
	}	

	@CachePut(value = CACHE_NAME)
	@Override
	public List<WorldData> repopulateCache() {
		log.info("In the repopulateCache method: " + LocalDateTime.now());
		return worldDataService.getDataFromWorldSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}		
}
