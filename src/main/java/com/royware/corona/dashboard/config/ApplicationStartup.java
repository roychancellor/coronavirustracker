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
import com.royware.corona.dashboard.interfaces.data.ICacheActions;

@Component
public class ApplicationStartup {
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier(value = "ca_world")
	private ICacheActions cacheActionsWorld;
	
	@Autowired
	@Qualifier(value = "ca_us")
	private ICacheActions cacheActionsUS;
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		log.info("Application has successfully started in environment: {}", env.getProperty("ENVIRONMENT"));
		log.info("About to initialize the WORLD cache with cacheActions object of class: {}", cacheActionsWorld.getClass().getSimpleName());
		cacheActionsWorld.setCleanData(false);
		cacheActionsWorld.populateCacheFromSource(CacheKeys.CACHE_KEY_WORLD.getName());
		log.info("About to initialize the USA cache with cacheActions object of class: {}", cacheActionsUS.getClass().getSimpleName());
		cacheActionsUS.setCleanData(Boolean.parseBoolean(env.getProperty("corona.filter.data")));
		cacheActionsUS.populateCacheFromSource(CacheKeys.CACHE_KEY_US.getName());
	}
}