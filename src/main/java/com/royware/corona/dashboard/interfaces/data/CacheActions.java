package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.WorldData;

@Service
public interface CacheActions {
	public static final String CACHE_NAME = "dataCache";
	
	public void cacheEvictAndRepopulate();
	public void evictCache();
	public List<WorldData> repopulateCache();
}
