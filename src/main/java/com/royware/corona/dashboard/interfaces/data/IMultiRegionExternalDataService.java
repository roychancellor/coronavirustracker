package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.UnitedStatesData;

@Service
public interface IMultiRegionExternalDataService {
	public String getStatesFromMultiRegionString(String region);
	public int getMultiRegionPopulation(String fullRegionName);
	public List<UnitedStatesData> getMultiRegionDataFromExternalSource(String fullRegionName, ExternalDataService dataService);
}
