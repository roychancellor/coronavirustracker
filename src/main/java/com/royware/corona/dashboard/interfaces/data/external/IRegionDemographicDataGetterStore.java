package com.royware.corona.dashboard.interfaces.data.external;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;

@Service
public interface IRegionDemographicDataGetterStore {
	public String getRegionFullNameFor(RegionsInDashboard region);
	public int getRegionPopulationFor(RegionsInDashboard region);
}
