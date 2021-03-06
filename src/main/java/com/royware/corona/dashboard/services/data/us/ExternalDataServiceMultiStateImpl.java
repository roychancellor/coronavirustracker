package com.royware.corona.dashboard.services.data.us;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Component("multiState")
public class ExternalDataServiceMultiStateImpl implements IExternalDataConnectionService {
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceMultiStateImpl.class);
	
	@Autowired
	@Qualifier(value = "singleState")
	private IExternalDataConnectionService singleStateDataService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String multiRegionState) {
		log.debug("MULTI_REGION: ABOUT TO HIT ENDPOINT FOR STATE DATA FOR: " + multiRegionState);
		List<UnitedStatesData> stateDataList = singleStateDataService.makeDataListFromExternalSource(multiRegionState);
		log.debug("FINISHED GETTING DATA FOR STATE: " + multiRegionState);
		
		return stateDataList;
	}
}
