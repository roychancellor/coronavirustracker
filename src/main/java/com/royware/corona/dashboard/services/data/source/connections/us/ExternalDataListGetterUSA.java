package com.royware.corona.dashboard.services.data.source.connections.us;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.interfaces.dashboard.IDashboardConfigService;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetterFactory;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionDataGetter;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;
import com.royware.corona.dashboard.services.data.cache.CacheManagerProvider;

@Component("us")
public class ExternalDataListGetterUSA implements IExternalDataListGetter {
	
	private ConcurrentMapCache cacheManager;
	private static final Logger log = LoggerFactory.getLogger(ExternalDataListGetterUSA.class);
	
	@Autowired
	private IMultiRegionDataGetter multiRegionDataGetter;
	
	@Autowired
	private IExternalDataListGetterFactory externalDataSourceFactory;

	private boolean toCleanNegativeChangesFromTotals;
	
	@Override
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals) {
		this.toCleanNegativeChangesFromTotals = cleanNegativeChangesFromTotals;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String cacheKey) {
		// This method will always pull data from the cache first. If the cache is empty, it will call
		// the external data getter for multi-region to populate the cache.
		cacheManager = CacheManagerProvider.getManager();
		List<UnitedStatesData> usaData = safeGetDataFromCache(cacheKey);
		if(usaData == null || usaData.isEmpty()) {
			log.info("US Data not in cache. Getting the USA data from its source (via multi-region).");
			multiRegionDataGetter.setCleanNegativeChangesFromTotals(toCleanNegativeChangesFromTotals);
			usaData = multiRegionDataGetter.getDataFor(
					IDashboardConfigService.ALL_STATES_AS_CSV,
					externalDataSourceFactory.create("MULTI") // which is just a wrapper for a single state external data source
				);
		} else {
			log.info("Returning the cached version of the USA data.");
		}
		return usaData;
	}
	
	@SuppressWarnings("unchecked")
	private List<UnitedStatesData> safeGetDataFromCache(String cacheKey) {
		ValueWrapper springCacheValueWrapper = cacheManager.get(cacheKey);
		if(springCacheValueWrapper == null) {
			return null;
		}
		return (List<UnitedStatesData>)springCacheValueWrapper.get();
	}
}
