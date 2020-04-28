package com.royware.corona.dashboard.services.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;

/**
 * Provides service methods for getting dashboard data from external sources
 */
public class SingleStateDataServiceImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesCases> makeDataListFromExternalSource(String stateAbbreviation) {
		log.info("***** ABOUT TO GET STATE: " + stateAbbreviation + " ****");
		UnitedStatesCases[] stateDataArray = restTemplate.getForObject(
				DataUrls.STATE_DATA_URL.toString() + stateAbbreviation.toUpperCase(),
				UnitedStatesCases[].class
		);
		List<UnitedStatesCases> stateDataList = new ArrayList<>(Arrays.asList(stateDataArray));
		Collections.reverse(stateDataList);
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDate() < US_CUTOFF_DATE));
		
//		for(UnitedStatesCases usc:stateDataList) {
//			log.info(usc.toString());
//		}
		
		log.info("***** FINISHED GETTING STATE: " + stateAbbreviation + " ****");
		
		return stateDataList;
	}
}
