package com.royware.corona.dashboard.interfaces;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.UnitedStatesData;
import com.royware.corona.dashboard.model.WorldData;

@Service
public interface DashboardDataService {
	public UnitedStatesData[] getAllUsData();
	public UnitedStatesData[] getAllUsDataExcludingState(String stateAbbreviation);
	public UnitedStatesData[] getSingleUsStateData(String stateAbbreviation);
	public WorldData[] getSingleNonUsCountryData(String countryName);
}
