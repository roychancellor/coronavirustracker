package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Service
public interface ICacheActions {
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvictAndRepopulate();
	public void evictCache();
	public <T extends CanonicalCaseDeathData> void populateCacheFromExistingData(String cacheName, List<T> newCacheData);
	public void populateCacheFromSource(String cacheName);
}
