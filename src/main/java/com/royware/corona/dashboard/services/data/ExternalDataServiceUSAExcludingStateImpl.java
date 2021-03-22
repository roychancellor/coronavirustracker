package com.royware.corona.dashboard.services.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Component("usExcludingState")
public class ExternalDataServiceUSAExcludingStateImpl implements ExternalDataService {
	@Autowired
	@Qualifier(value = "us")
	private ExternalDataService usDataService;
	
	@Autowired
	@Qualifier(value = "singleState")
	private ExternalDataService stateDataService;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceUSAExcludingStateImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String stateToExclude) {
		//call getAllUsData, then call the states API and subtract out the state numbers
		log.info("***** ABOUT TO <<<FILTER OUT>>> STATE: " + stateToExclude + " ****");
		List<UnitedStatesData> usDataExcludingState = usDataService.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_US.getName());
		List<UnitedStatesData> stateDataToExclude = stateDataService.makeDataListFromExternalSource(stateToExclude);
		
		for(int i = 0; i < usDataExcludingState.size(); i++) {
			usDataExcludingState.get(i).setTotalPositiveCases(
				usDataExcludingState.get(i).getTotalPositiveCases() - stateDataToExclude.get(i).getTotalPositiveCases()
			);
			usDataExcludingState.get(i).setTotalNegativeCases(
				usDataExcludingState.get(i).getTotalNegativeCases() - stateDataToExclude.get(i).getTotalNegativeCases()
			);
			usDataExcludingState.get(i).setTotalDeaths(
				usDataExcludingState.get(i).getTotalDeaths() - stateDataToExclude.get(i).getTotalDeaths()
			);
		}
		
		return usDataExcludingState;
	}
}
