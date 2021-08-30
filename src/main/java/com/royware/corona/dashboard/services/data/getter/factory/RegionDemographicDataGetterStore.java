package com.royware.corona.dashboard.services.data.getter.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.external.IRegionDemographicDataGetterFactory;
import com.royware.corona.dashboard.interfaces.data.external.IRegionDemographicDataGetterStore;

@Component
public class RegionDemographicDataGetterStore implements IRegionDemographicDataGetterStore {

	@Autowired private IRegionDemographicDataGetterFactory regionDemographicsFactory;
	
	@Override
	public String getRegionFullNameFor(RegionsInDashboard region) {
		return regionDemographicsFactory.getRegionDataFor(region).getFullName();
	}

	@Override
	public int getRegionPopulationFor(RegionsInDashboard region) {
		return regionDemographicsFactory.getRegionDataFor(region).getPopulation();
	}
}
