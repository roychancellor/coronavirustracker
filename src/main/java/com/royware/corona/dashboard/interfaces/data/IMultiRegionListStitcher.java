package com.royware.corona.dashboard.interfaces.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Service
public interface IMultiRegionListStitcher {

	List<UnitedStatesData> stitchMultiStateListsIntoOneList(Map<String, List<UnitedStatesData>> mapOfStateDataLists, String[] states);

	Map<String, List<UnitedStatesData>> makeMapOfStateDataLists(IExternalDataConnectionService dataService, String[] states);
	
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals);

}