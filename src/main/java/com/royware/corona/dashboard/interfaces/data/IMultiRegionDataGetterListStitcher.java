package com.royware.corona.dashboard.interfaces.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Service
public interface IMultiRegionDataGetterListStitcher {

	List<UnitedStatesData> stitchMultiStateListsIntoOneList(Map<String, List<UnitedStatesData>> mapOfStateDataLists, String[] states);

	Map<String, List<UnitedStatesData>> makeMapOfStateDataLists(IExternalDataListGetter dataService, String[] states);
	
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals);

}