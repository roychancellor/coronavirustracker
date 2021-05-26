package com.royware.corona.dashboard.services.data.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.data.IExternalDataServiceFactory;

@Service
public class ExternalDataServiceFactoryImpl implements IExternalDataServiceFactory {
	@Autowired
	@Qualifier(value = "us")
	private IExternalDataConnectionService usDataService;
	
	@Autowired
	@Qualifier(value = "singleState")
	private IExternalDataConnectionService singleStateDataService;
	
	@Autowired
	@Qualifier(value = "multiState")
	private IExternalDataConnectionService multiStateDataService;
	
	@Autowired
	@Qualifier(value = "usExcludingState")
	private IExternalDataConnectionService usExcludingStateDataService;
	
	@Autowired
	@Qualifier(value = "singleCountry")
	private IExternalDataConnectionService singleCountryDataService;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceFactoryImpl.class);
	
	@Override
	public IExternalDataConnectionService getExternalDataService(String regionOfService) {
		IExternalDataConnectionService dataService;
		
		log.debug("getExternalDataService trying to make dataService for " + regionOfService);
		if(regionOfService.equalsIgnoreCase(RegionsInDashboard.USA.name())) {
			log.debug("Making dataService for " + RegionsInDashboard.USA.name());
			dataService = usDataService;
		} else if(regionOfService.equalsIgnoreCase(RegionsInDashboard.USA_NO_NY.name())) {
			dataService = usExcludingStateDataService;
		} else if(regionOfService.length() == 2) {
			dataService = singleStateDataService;
		} else if(regionOfService.length() == 3){
			dataService = singleCountryDataService;
		} else if(regionOfService.substring(0,5).equalsIgnoreCase("MULTI")) {
			dataService = multiStateDataService;
		} else {
			log.error("getExternalDataService NO MATCHES to regionOfService: '" + regionOfService + "'. Throwing exception!!!");
			throw new IllegalArgumentException("NO MATCH TO regionOfService");
		}
		
		return dataService;
	}
}
