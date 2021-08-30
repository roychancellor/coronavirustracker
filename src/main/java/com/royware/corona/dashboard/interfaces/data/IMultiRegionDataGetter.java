package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Service
public interface IMultiRegionDataGetter {
	public String getStatesFromMultiRegionString(String region);
	public int getMultiRegionPopulation(String fullRegionName);
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals);
	public List<UnitedStatesData> getDataFor(String fullRegionName, IExternalDataListGetter dataService);
}
