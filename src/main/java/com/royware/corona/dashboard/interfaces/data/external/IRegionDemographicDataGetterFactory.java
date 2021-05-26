package com.royware.corona.dashboard.interfaces.data.external;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.model.data.common.RegionData;

@Service
public interface IRegionDemographicDataGetterFactory {
	public RegionData getRegionDataFor(RegionsInDashboard region);
}
