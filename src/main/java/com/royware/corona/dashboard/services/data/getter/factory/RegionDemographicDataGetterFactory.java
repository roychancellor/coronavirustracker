package com.royware.corona.dashboard.services.data.getter.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.external.IRegionDemographicDataGetterFactory;
import com.royware.corona.dashboard.model.data.common.RegionData;

@Component
public class RegionDemographicDataGetterFactory implements IRegionDemographicDataGetterFactory {			
	@Override
	public RegionData getRegionDataFor(RegionsInDashboard region) {
		return new RegionData(region.getPopulation(), region.getRegionType(), region.getLabel());
	}
}
