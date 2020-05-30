package com.royware.corona.dashboard.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;

@Component
public class CacheInit {
	@Autowired
	@Qualifier("world")
	private ExternalDataService worldDataService;
	private static final Logger log = LoggerFactory.getLogger(CacheInit.class);
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		log.info("Application has successfully started in environment: {}", System.getenv("ENVIRONMENT"));
		log.info("In CacheInit class: worldDataService hashcode: " + this.hashCode());
		worldDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}
}