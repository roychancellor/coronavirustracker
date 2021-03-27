package com.royware.corona.dashboard.services.data.us;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.DataFields;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionListStitcher;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Component
public class MultiRegionListStitcherImpl implements IMultiRegionListStitcher {
	private static final Logger log = LoggerFactory.getLogger(MultiRegionExternalDataServiceImpl.class);
	
	@Override
	public List<UnitedStatesData> stitchMultiStateListsIntoOneList(Map<String, List<UnitedStatesData>> mapOfStateDataLists, String[] states) {
		List<UnitedStatesData> multiRegionDataListToReturn = new ArrayList<>();
		Map<Integer, Integer> sumPositiveCases = new TreeMap<>();
		Map<Integer, Integer> sumNegativeCases = new TreeMap<>();
		Map<Integer, Integer> sumPosNegCases = new TreeMap<>();
		Map<Integer, Integer> sumPendingTests = new TreeMap<>();
		Map<Integer, Integer> sumDeaths = new TreeMap<>();
		
		int latestDateValueCases = getLatestDate(mapOfStateDataLists, states, DataFields.CASES);
		int latestDateValueDeaths = getLatestDate(mapOfStateDataLists, states, DataFields.DEATHS);
		log.debug("Found the latest date for cases: " + latestDateValueCases + " and the latest date for deaths: " + latestDateValueDeaths);
		
		//Go through the state data lists and create a list of UnitedStatesData objects whose
		//cases and deaths are the sum of cases and deaths for each state AND where the date > latest date
		for(String state : states) {
			for(UnitedStatesData usd : mapOfStateDataLists.get(state)) {
				Integer dateInteger = usd.getDateInteger();
				if(usd.getDateInteger() >= latestDateValueCases) {
					if(sumPositiveCases.containsKey(dateInteger)) {
						sumPositiveCases.put(dateInteger, sumPositiveCases.get(dateInteger) + usd.getTotalPositiveCases());
					} else {
						sumPositiveCases.put(dateInteger, usd.getTotalPositiveCases());
					}
					if(sumNegativeCases.containsKey(dateInteger)) {
						sumNegativeCases.put(dateInteger, sumNegativeCases.get(dateInteger) + usd.getTotalNegativeCases());
					} else {
						sumNegativeCases.put(dateInteger, usd.getTotalNegativeCases());
					}
					if(sumPosNegCases.containsKey(dateInteger)) {
						sumPosNegCases.put(dateInteger, sumPosNegCases.get(dateInteger) + usd.getTotalPositivePlusNegative());
					} else {
						sumPosNegCases.put(dateInteger, usd.getTotalPositivePlusNegative());
					}
					if(sumPendingTests.containsKey(dateInteger)) {
						sumPendingTests.put(dateInteger, sumPendingTests.get(dateInteger) + usd.getPendingTests());
					} else {
						sumPendingTests.put(dateInteger, usd.getPendingTests());
					}
				}
				if(usd.getDateInteger() >= latestDateValueDeaths && usd.getDateInteger() >= latestDateValueCases) {
					if(sumDeaths.containsKey(dateInteger)) {
						sumDeaths.put(dateInteger, sumDeaths.get(dateInteger) + usd.getTotalDeaths());
					} else {
						sumDeaths.put(dateInteger, usd.getTotalDeaths());
					}
				}
			}			
		}
		log.debug("Made all the DATA FIELD maps containing the SUMS for all states in the multi-region");
		
		//NOW, iterate through the keys from the latest case date through the current day as an integer
		//and construct a list of UnitedStatesData objects that will contain the sum for the whole region for each day
		//Get all available date strings and create a list of UnitedStatesData objects from the various maps
		for(Integer dateInteger : sumPositiveCases.keySet()) {
			multiRegionDataListToReturn.add(new UnitedStatesData());
			UnitedStatesData thisDateForRegion = multiRegionDataListToReturn.get(multiRegionDataListToReturn.size() - 1);
			LocalDate localDate = localDateFromStringDate(dateInteger + "");
			thisDateForRegion.setDateTimeString(localDate.toString());
			thisDateForRegion.setDateChecked(localDate);
			thisDateForRegion.setDateInteger(dateInteger);
			thisDateForRegion.setTotalPositiveCases(sumPositiveCases.get(dateInteger));
			thisDateForRegion.setTotalNegativeCases(sumNegativeCases.get(dateInteger));
			thisDateForRegion.setTotalPositivePlusNegative(sumPosNegCases.get(dateInteger));
			thisDateForRegion.setPendingTests(sumPendingTests.get(dateInteger));
			if(sumDeaths.containsKey(dateInteger)) {
				thisDateForRegion.setTotalDeaths(sumDeaths.get(dateInteger));
			} else {
				thisDateForRegion.setTotalDeaths(0);
			}
		}
		log.debug("Finished making the region data list and ready to return it.");
		
		return multiRegionDataListToReturn;
	}

	@Override
	public Map<String, List<UnitedStatesData>> makeMapOfStateDataLists(ExternalDataService dataService, String[] states) {
		Map<String, List<UnitedStatesData>> stateDataLists = new HashMap<>();
		
		//Make a map where the key is the state and the value is the list of data for the state
		log.debug("The array of states for getting data is:");
		for(String state : states) {
			log.info(state);
			stateDataLists.put(state, dataService.makeDataListFromExternalSource(state));
		}
		log.debug("Made the map containing all state data lists");
		return stateDataLists;
	}

	private int getLatestDate(Map<String, List<UnitedStatesData>> stateDataLists, String[] states, DataFields field) {
		int latestDateValue = 20200101;
		//Iterate through the state data lists and find the latest (maximum) date for when cases > 0 and deaths > 0
		for(String state : states) {
			for(UnitedStatesData usd : stateDataLists.get(state)) {
				int testValue = 0;
				if(field == DataFields.CASES) {
					testValue = usd.getTotalPositiveCases();
				} else if(field == DataFields.DEATHS) {
					testValue = usd.getTotalDeaths();
				}
				if(usd != null && testValue > 0) {
					if(usd.getDateInteger() > latestDateValue) {
						latestDateValue = usd.getDateInteger();
					}
					break;
				}
			}
		}
		return latestDateValue;
	}

	private LocalDate localDateFromStringDate(String dateString) {
		//Brings in a string of the form 20200506 and makes a local date
		return LocalDate.of(Integer.parseInt(dateString.substring(0,4)),
				Integer.parseInt(dateString.substring(4,6)), Integer.parseInt(dateString.substring(6)));
	}	
}
