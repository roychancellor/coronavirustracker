package com.royware.corona.dashboard.services.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private ExternalDataServiceFactory dataService;

	//Pull data directly from the cache always
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String cacheKey) {
		cacheManager = CacheManagerProvider.getManager();
		List<UnitedStatesData> usaData = (List<UnitedStatesData>)cacheManager.get(cacheKey).get();
		if(usaData == null || usaData.isEmpty()) {
			log.info("Getting the USA data from its source (via multi-region), then putting it into the cache.");
			usaData = multiRegionDataService.getMultiRegionDataFromExternalSource(
					DashboardConfigService.ALL_STATES_AS_CSV,
					dataService.getExternalDataService("MULTI")
			);
			cacheManager.put(cacheKey, usaData);
		}
		log.info("Returning the cached version of the USA data.");
		return usaData;
	}
}
