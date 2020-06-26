package com.royware.corona.dashboard.services.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Component("multiState")
public class ExternalDataServiceMultiStateImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceMultiStateImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String multiRegionState) {
		String url = DataUrls.STATE_DATA_URL_START.getName() + multiRegionState.toLowerCase() + DataUrls.STATE_DATA_URL_END.getName();
		log.info("***** MULTI_REGION: ABOUT TO HIT ENDPOINT FOR STATE DATA AT " + url + " FOR " + multiRegionState);
		UnitedStatesData[] stateDataArray = restTemplate.getForObject(url, UnitedStatesData[].class);
		List<UnitedStatesData> stateDataList = new ArrayList<>(Arrays.asList(stateDataArray));
		Collections.reverse(stateDataList);
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDateInteger() < US_CUTOFF_DATE));
		
		log.info("***** FINISHED GETTING STATE: " + multiRegionState + " ****");
		
		return stateDataList;
	}
}