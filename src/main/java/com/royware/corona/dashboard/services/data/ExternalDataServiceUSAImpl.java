package com.royware.corona.dashboard.services.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.interfaces.dashboard.DashboardConfigService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionExternalDataService;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

@Component("us")
public class ExternalDataServiceUSAImpl implements ExternalDataService {
	
	private ConcurrentMapCache cacheManager;
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceUSAImpl.class);
	
	@Autowired
	private IMultiRegionExternalDataService multiRegionDataService;
	
	@Autowired
	private ExternalDataServiceFactory dataService;

	//Pull data directly from the cache always
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String cacheKey) {
		cacheManager = CacheManagerProvider.getManager();
		List<UnitedStatesData> usaData = safeGetDataFromCache(cacheKey);
		if(usaData == null || usaData.isEmpty()) {
			log.info("Getting the USA data from its source (via multi-region).");
			usaData = multiRegionDataService.getMultiRegionDataFromExternalSource(
					DashboardConfigService.ALL_STATES_AS_CSV,
					dataService.getExternalDataService("MULTI")
			);
			//cacheManager.put(cacheKey, usaData);
		}
		log.info("Returning the cached version of the USA data.");
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
