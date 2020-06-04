package com.royware.corona.dashboard.interfaces.data;

import org.springframework.stereotype.Service;

@Service
public interface CacheActions {
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvictAndRepopulate();
	public void evictCache();
	public void repopulateCache(String cacheKey);
}
