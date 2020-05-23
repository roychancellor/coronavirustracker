package com.royware.corona.dashboard.beans;

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
	ExternalDataService worldDataService;
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		worldDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_WORLD.getName());
	}
}