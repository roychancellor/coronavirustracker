package com.royware.corona.dashboard.services.data.getter.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterFactory;
import com.royware.corona.dashboard.interfaces.data.external.IDataGetterStore;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DataGetterStore implements IDataGetterStore {
	@Autowired private IExternalDataGetterFactory dataGetterFactory;
	
	@Override
	public <T extends ICanonicalCaseDeathData> List<T> getDataFor(
			RegionsInDashboard region,
			IExternalDataListGetter externalDataService) {
		
		return dataGetterFactory.create(region).getDataUsing(externalDataService, region);
	}	
}
