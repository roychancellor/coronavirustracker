package com.royware.corona.dashboard.services.data.source.connection.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetterFactory;

@Service
public class ExternalDataListGetterFactory implements IExternalDataListGetterFactory {
	@Autowired
	@Qualifier(value = "us")
	private IExternalDataListGetter usDataListGetter;
	
	@Autowired
	@Qualifier(value = "singleState")
	private IExternalDataListGetter singleStateDataListGetter;
	
	@Autowired
	@Qualifier(value = "multiState")
	private IExternalDataListGetter multiStateDataListGetter;
	
	@Autowired
	@Qualifier(value = "usExcludingState")
	private IExternalDataListGetter usExcludingStateDataListGetter;
	
	/*@Autowired
	@Qualifier(value = "singleCountry")
	private IExternalDataListGetter singleCountryDataSource;*/
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataListGetterFactory.class);
	
	@Override
	public IExternalDataListGetter create(String regionOfService) {
		IExternalDataListGetter dataService;
		
		log.debug("getExternalDataService trying to make dataService for " + regionOfService);
		if(regionOfService.equalsIgnoreCase(RegionsInDashboard.USA.name())) {
			log.debug("Making dataService for " + RegionsInDashboard.USA.name());
			dataService = usDataListGetter;
		} else if(regionOfService.equalsIgnoreCase(RegionsInDashboard.USA_NO_NY.name())) {
			dataService = usExcludingStateDataListGetter;
		} else if(regionOfService.length() == 2) {
			dataService = singleStateDataListGetter;
		} /*else if(regionOfService.length() == 3){
			dataService = singleCountryDataSource;
		}*/ else if(regionOfService.substring(0,5).equalsIgnoreCase("MULTI")) {
			dataService = multiStateDataListGetter;
		} else {
			log.error("getExternalDataService NO MATCHES to regionOfService: '" + regionOfService + "'. Throwing exception!!!");
			throw new IllegalArgumentException("NO MATCH TO regionOfService");
		}
		
		return dataService;
	}
}
