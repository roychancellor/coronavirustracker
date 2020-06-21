package com.royware.corona.dashboard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.CacheActions;

@Component
public class ApplicationStartup {
	@Autowired
	private Environment env;
	
	@Autowired
	private CacheActions cacheActions;
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		log.info("Application has successfully started in environment: {}", env.getProperty("ENVIRONMENT"));
		log.info("About to initialize the cache with cacheActions object of class: {}", cacheActions.getClass().getSimpleName());
		cacheActions.populateCacheFromSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}
}