package com.royware.corona.dashboard.services.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.GeographicalRegions;
import com.royware.corona.dashboard.enums.Regions;
import com.royware.corona.dashboard.interfaces.DashboardMultiRegionService;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesData;

@Component
public class DashboardMultiRegionServiceImpl implements DashboardMultiRegionService {

	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

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
					GeographicalRegions regionEnum = GeographicalRegions.valueOfLabel(regions[i]);
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
			GeographicalRegions regionEnum = GeographicalRegions.valueOfLabel(regionsOnly);
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
			sumPop += Regions.valueOf(state).getRegionData().getPopulation();
		}
		return sumPop;
	}

	private String[] makeUniqueArrayOfStates(String fullRegionName) {
		//Make a set of unique state names, then put into an array
		Set<String> stateSet = new HashSet<>(Arrays.asList(fullRegionName.split(",")));
		String[] states = new String[stateSet.size()];
		stateSet.toArray(states);
		return states;
	}
	
	@Override
	public List<UnitedStatesData> getMultiRegionDataFromExternalSource(String fullRegionName,
			ExternalDataService dataService) {
		List<UnitedStatesData> multiRegionDataList = new ArrayList<>();
		Map<String, List<UnitedStatesData>> stateDataLists = new HashMap<String, List<UnitedStatesData>>();
		
		String[] states = makeUniqueArrayOfStates(fullRegionName);
		
		//Make a map where the key is the state and the value is the list of data for the state
		log.info("The array of states for getting data is:");
		for(String state : states) {
			log.info(state);
			stateDataLists.put(state, dataService.makeDataListFromExternalSource(state));
		}
		log.info("Made the map containing all state data lists");
		
		//Iterate through the state data lists and find the latest (maximum) date for when cases > 0 and deaths > 0
		int latestDateValueCases = 20200101;
		int latestDateValueDeaths = 20200101;
		for(String state : states) {
			for(UnitedStatesData usd : stateDataLists.get(state)) {
				if(usd != null && usd.getTotalPositiveCases() > 0) {
					if(usd.getDateInteger() > latestDateValueCases) {
						latestDateValueCases = usd.getDateInteger();
					}
					break;
				}
			}
		}
		for(String state : states) {
			for(UnitedStatesData usd : stateDataLists.get(state)) {
				if(usd.getTotalDeaths() > 0) {
					if(usd.getDateInteger() > latestDateValueDeaths) {
						latestDateValueDeaths = usd.getDateInteger();
					}
					break;
				}
			}
		}
		log.info("Found the latest date for cases: " + latestDateValueCases + " and the latest date for deaths: " + latestDateValueDeaths);
		
		//Go through the state data lists and create a list of UnitedStatesData objects whose
		//cases and deaths are the sum of cases and deaths for each state AND where the date > latest date
		Map<Integer, Integer> regionPositiveCases = new TreeMap<>();
		Map<Integer, Integer> regionNegativeCases = new TreeMap<>();
		Map<Integer, Integer> regionPosNegCases = new TreeMap<>();
		Map<Integer, Integer> regionPendingTests = new TreeMap<>();
		Map<Integer, Integer> regionDeaths = new TreeMap<>();
		for(String state : states) {
			for(UnitedStatesData usd : stateDataLists.get(state)) {
				Integer dateInteger = usd.getDateInteger();
				if(usd.getDateInteger() >= latestDateValueCases) {
					if(regionPositiveCases.containsKey(dateInteger)) {
						regionPositiveCases.put(dateInteger, regionPositiveCases.get(dateInteger) + usd.getTotalPositiveCases());
					} else {
						regionPositiveCases.put(dateInteger, usd.getTotalPositiveCases());
					}
					if(regionNegativeCases.containsKey(dateInteger)) {
						regionNegativeCases.put(dateInteger, regionNegativeCases.get(dateInteger) + usd.getTotalNegativeCases());
					} else {
						regionNegativeCases.put(dateInteger, usd.getTotalNegativeCases());
					}
					if(regionPosNegCases.containsKey(dateInteger)) {
						regionPosNegCases.put(dateInteger, regionPosNegCases.get(dateInteger) + usd.getTotalPositivePlusNegative());
					} else {
						regionPosNegCases.put(dateInteger, usd.getTotalPositivePlusNegative());
					}
					if(regionPendingTests.containsKey(dateInteger)) {
						regionPendingTests.put(dateInteger, regionPendingTests.get(dateInteger) + usd.getPendingTests());
					} else {
						regionPendingTests.put(dateInteger, usd.getPendingTests());
					}
				}
				if(usd.getDateInteger() >= latestDateValueDeaths && usd.getDateInteger() >= latestDateValueCases) {
					if(regionDeaths.containsKey(dateInteger)) {
						regionDeaths.put(dateInteger, regionDeaths.get(dateInteger) + usd.getTotalDeaths());
					} else {
						regionDeaths.put(dateInteger, usd.getTotalDeaths());
					}
				}
			}			
		}
		log.info("Made all the DATA FIELD maps containing the SUMS for all states in the multi-region");
		
		//NOW, iterate through the keys from the latest case date through the current day as an integer
		//and construct a list of UnitedStatesData objects that will contain the sum for the whole region for each day
		//Get all available date strings and create a list of UnitedStatesData objects from the various maps
		for(Integer dateInteger : regionPositiveCases.keySet()) {
			multiRegionDataList.add(new UnitedStatesData());
			UnitedStatesData thisItem = multiRegionDataList.get(multiRegionDataList.size() - 1);
			LocalDate localDate = localDateFromStringDate(dateInteger + "");
			thisItem.setDateTimeString(localDate.toString());  //also sets dateChecked
			thisItem.setTotalPositiveCases(regionPositiveCases.get(dateInteger));
			thisItem.setTotalNegativeCases(regionNegativeCases.get(dateInteger));
			thisItem.setTotalPositivePlusNegative(regionPosNegCases.get(dateInteger));
			thisItem.setPendingTests(regionPendingTests.get(dateInteger));
			if(regionDeaths.containsKey(dateInteger)) {
				thisItem.setTotalDeaths(regionDeaths.get(dateInteger));
			} else {
				thisItem.setTotalDeaths(0);
			}
		}
		log.info("Finished making the region data list and ready to return it.");
		
		return multiRegionDataList;
	}

	private LocalDate localDateFromStringDate(String dateString) {
		//Brings in a string of the form 20200506 and makes a local date
		return LocalDate.of(Integer.parseInt(dateString.substring(0,4)),
				Integer.parseInt(dateString.substring(4,6)), Integer.parseInt(dateString.substring(6)));
	}	
}
