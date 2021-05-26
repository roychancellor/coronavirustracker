package com.royware.corona.dashboard.services.data.external.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterFactory;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterStore;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class ExternalDataGetterStore implements IExternalDataGetterStore {
	@Autowired private IExternalDataGetterFactory dataGetterFactory;
	
	@Override
	public <T extends CanonicalCaseDeathData> List<T> getDataFor(
			RegionsInDashboard region,
			IExternalDataConnectionService externalDataService) {
		
		return dataGetterFactory.create(region).getDataUsing(externalDataService, region);
	}	
}
