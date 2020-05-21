package com.royware.corona.dashboard.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public interface DashboardMultiRegionService {
	public String getStatesFromMultiRegionString(String region);
	public int getMultiRegionPopulation(String fullRegionName);
	public List<UnitedStatesData> getMultiRegionDataFromExternalSource(String fullRegionName, ExternalDataService dataService);
}
