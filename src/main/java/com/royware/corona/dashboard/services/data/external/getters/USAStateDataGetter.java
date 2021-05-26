package com.royware.corona.dashboard.services.data.external.getters;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetter;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

public class USAStateDataGetter implements IExternalDataGetter {

	@Override
	public <T extends CanonicalCaseDeathData> List<T> getDataUsing(IExternalDataConnectionService eds, RegionsInDashboard region) {
		Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
		log.info(this.getClass().getSimpleName() + " is about to call makeDataListFromExternalSource with " + eds.toString());
		return eds.makeDataListFromExternalSource(region.name());
	}
}
