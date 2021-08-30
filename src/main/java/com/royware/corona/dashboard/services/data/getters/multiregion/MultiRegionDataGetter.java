package com.royware.corona.dashboard.services.data.getters.multiregion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.UsGeoRegions;
import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionDataGetter;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionDataGetterListStitcher;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Component
public class MultiRegionDataGetter implements IMultiRegionDataGetter {

	private static final Logger log = LoggerFactory.getLogger(MultiRegionDataGetter.class);
	
	@Autowired
	private IMultiRegionDataGetterListStitcher listStitcher;
	
	private boolean toCleanNegativeChangesFromTotals = false;
	
	private String multiRegionCSV;
	
	@Override
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals) {
		this.toCleanNegativeChangesFromTotals = cleanNegativeChangesFromTotals;
	}
	
	@Override
	public String getStatesFromMultiRegionString(String region) {
		String regionsOnly = region.substring(region.indexOf(':') + 1);
		if(regionsOnly.contains(",")) {
			if(regionsOnly.indexOf(",") == 2) {
				return regionsOnly;
			}
			
			StringBuilder sb = new StringBuilder();
			String[] regions = regionsOnly.split(",");
			for(int i = 0; i < regions.length; i++) {
				if(regions[i].length() == 2) {
					sb.append(regions[i]);
				} else {
					UsGeoRegions regionEnum = UsGeoRegions.valueOfLabel(regions[i]);
					sb.append(regionEnum.getStatesInRegion(regionEnum.getLabel()));
				}
				if(i < regions.length - 1) {
					sb.append(",");
				}
			}
			multiRegionCSV = sb.toString();
			return multiRegionCSV;
		} else {
			//Determine if it's a single state picked from the drop-down or a pre-defined geographical region
			if(regionsOnly.length() == 2) {
				return regionsOnly;
			}
			UsGeoRegions regionEnum = UsGeoRegions.valueOfLabel(regionsOnly);
			if(regionEnum == null) {
				return null;
			}
			return regionEnum.getStatesInRegion(regionEnum.getLabel());
		}
	}

	@Override
	public int getMultiRegionPopulation(String fullRegionName) {
		String[] arrayOfStates = makeUniqueArrayOfStates(fullRegionName);
		//Split the full region name into individual states, then iterate through the states and sum their populations
		int sumPop = 0;
		for(String state : arrayOfStates) {
			sumPop += RegionsInDashboard.valueOf(state).getPopulation();
		}
		return sumPop;
	}

	private String[] makeUniqueArrayOfStates(String fullRegionNameCSV) {
		//Make a set of unique state names, then put into an array
		Set<String> stateSet = new HashSet<>(Arrays.asList(fullRegionNameCSV.split(",")));
		String[] states = new String[stateSet.size()];
		stateSet.toArray(states);
		return states;
	}
	
	@Override
	public List<UnitedStatesData> getDataFor(String fullRegionName, IExternalDataListGetter dataService) {
		List<UnitedStatesData> multiRegionDataList = new ArrayList<>();
		Map<String, List<UnitedStatesData>> mapOfStateDataLists = new HashMap<String, List<UnitedStatesData>>();
		
		log.info("Getting the multi-region data from an external source");
		String[] states = makeUniqueArrayOfStates(fullRegionName);
		
		// Set the switch to filter negative changes from the final stitched list
		listStitcher.setCleanNegativeChangesFromTotals(toCleanNegativeChangesFromTotals);
		
		mapOfStateDataLists = listStitcher.makeMapOfStateDataLists(dataService, states);
		
		multiRegionDataList = listStitcher.stitchMultiStateListsIntoOneList(mapOfStateDataLists, states);
				
		log.info("Finished getting the multi-region data from an external source");
		return multiRegionDataList;
	}
}
