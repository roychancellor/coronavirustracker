package com.royware.corona.dashboard.services.data;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import com.royware.corona.dashboard.interfaces.data.CacheActions;

public class CacheManagerProvider {
	private static ConcurrentMapCache cacheManager = null;
	
	public static ConcurrentMapCache getManager() {
		if(cacheManager == null) {
	        cacheManager = new ConcurrentMapCache(CacheActions.CACHE_NAME);
		}
		return cacheManager;
	}
}
