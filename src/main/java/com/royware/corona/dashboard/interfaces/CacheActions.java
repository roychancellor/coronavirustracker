package com.royware.corona.dashboard.interfaces;

public interface CacheActions {
	public static final long CACHE_EVICT_PERIOD_MILLISECONDS = 3 * 60 * 60 * 1000;  //every 3 hours
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvict();
}
