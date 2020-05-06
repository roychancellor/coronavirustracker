package com.royware.corona.dashboard.services.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.Regions;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.interfaces.ExternalDataServiceFactory;

@Service
public class ExternalDataServiceFactoryImpl implements ExternalDataServiceFactory {
	@Autowired
	@Qualifier(value = "us")
	private ExternalDataService usDataService;
	
	@Autowired
	@Qualifier(value = "singleState")
	private ExternalDataService singleStateDataService;
	
	@Autowired
	@Qualifier(value = "usExcludingState")
	private ExternalDataService usExcludingStateDataService;
	
	@Autowired
	@Qualifier(value = "singleCountry")
	private ExternalDataService singleCountryDataService;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceFactoryImpl.class);
	
	@Override
	public ExternalDataService getExternalDataService(String regionOfService) {
		ExternalDataService dataService;
		
		log.info("getExternalDataService trying to make dataService for " + regionOfService);
		if(regionOfService.equalsIgnoreCase(Regions.USA.name())) {
			log.info("Making dataService for " + Regions.USA.name());
			dataService = usDataService;
		} else if(regionOfService.equalsIgnoreCase(Regions.USA_NO_NY.name())) {
			dataService = usExcludingStateDataService;
		} else if(regionOfService.length() == 2) {
			dataService = singleStateDataService;
		} else if(regionOfService.length() == 3){
			dataService = singleCountryDataService;
		} else if(regionOfService.substring(0,5).equalsIgnoreCase("MULTI")) {
			log.info("STUB OUT ONLY!!! Making dataService for " + regionOfService + " (but not really, just returning usDataService)");
			dataService = usDataService;
		} else {
			log.info("getExternalDataService NO MATCHES to regionOfService: '" + regionOfService + "'. Throwing exception!!!");
			throw new IllegalArgumentException("NO MATCH TO regionOfService");
		}
		
		return dataService;
	}

}
