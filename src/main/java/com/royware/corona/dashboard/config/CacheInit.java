package com.royware.corona.dashboard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;

@Component
public class CacheInit {
	@Autowired
	@Qualifier("world")
	private ExternalDataService worldDataService;
	
	@Autowired
	private Environment env;
	
	private static final Logger log = LoggerFactory.getLogger(CacheInit.class);
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		log.info("Application has successfully started in environment: {}", env.getProperty("ENVIRONMENT"));
		log.info("In CacheInit class: worldDataService hashcode: " + this.hashCode());
		worldDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}
}