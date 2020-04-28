package com.royware.corona.dashboard.services.data;

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
	
	@Override
	public ExternalDataService getExternalDataService(String regionOfService) {
		ExternalDataService dataService;
		
		if(regionOfService.equalsIgnoreCase(Regions.USA.name())) {
			dataService = usDataService;
		} else if(regionOfService.equalsIgnoreCase(Regions.USA_NO_NY.name())) {
			dataService = usExcludingStateDataService;
		} else if(regionOfService.length() == 2) {
			dataService = singleStateDataService;
		} else if(regionOfService.length() == 3){
			dataService = singleCountryDataService;
		} else {
			throw new IllegalArgumentException();
		}
		
		return dataService;
	}

}
