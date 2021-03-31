package com.royware.corona.dashboard.services.data.cache;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import com.royware.corona.dashboard.interfaces.data.ICacheActions;

public class CacheManagerProvider {
	private static ConcurrentMapCache cacheManager = null;
	
	public static ConcurrentMapCache getManager() {
		if(cacheManager == null) {
	        cacheManager = new ConcurrentMapCache(ICacheActions.CACHE_NAME);
		}
		return cacheManager;
	}
}
