package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Service
public interface ICacheActions {
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvictAndRepopulate();
	public void evictCache();
	public <T extends ICanonicalCaseDeathData> void populateCacheFromDataList(String cacheName, List<T> newCacheData);
	public void populateCacheFromSource(String cacheName);
}
