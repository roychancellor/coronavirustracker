package com.royware.corona.dashboard.interfaces;

public interface CacheActions {
	public static final long CACHE_EVICT_PERIOD_MILLISECONDS_PROD = 3 * 60 * 60 * 1000;  //every 3 hours
	public static final long CACHE_EVICT_PERIOD_MILLISECONDS_DEV = 15 * 1000;  //every 60 seconds
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvictAndRepopulate();
}
