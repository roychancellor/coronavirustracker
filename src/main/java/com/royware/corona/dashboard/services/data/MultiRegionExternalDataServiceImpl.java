package com.royware.corona.dashboard.services.data;

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
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionExternalDataService;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionListStitcher;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

@Component
public class MultiRegionExternalDataServiceImpl implements IMultiRegionExternalDataService {

	private static final Logger log = LoggerFactory.getLogger(MultiRegionExternalDataServiceImpl.class);
	
	@Autowired
	private IMultiRegionListStitcher listStitcher;

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
			return sb.toString();
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
			sumPop += RegionsData.valueOf(state).getRegionData().getPopulation();
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
	public List<UnitedStatesData> getMultiRegionDataFromExternalSource(String fullRegionName, ExternalDataService dataService) {
		List<UnitedStatesData> multiRegionDataList = new ArrayList<>();
		Map<String, List<UnitedStatesData>> mapOfStateDataLists = new HashMap<String, List<UnitedStatesData>>();
		
		log.info("Getting the multi-region data from an external source");
		String[] states = makeUniqueArrayOfStates(fullRegionName);
		
		mapOfStateDataLists = listStitcher.makeMapOfStateDataLists(dataService, states);
		
		multiRegionDataList = listStitcher.stitchMultiStateListsIntoOneList(mapOfStateDataLists, states);
		
		log.info("Finished getting the multi-region data from an external source");
		return multiRegionDataList;
	}

}
