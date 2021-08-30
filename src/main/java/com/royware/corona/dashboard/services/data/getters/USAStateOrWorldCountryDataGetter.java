package com.royware.corona.dashboard.services.data.getters;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.external.IDataGetter;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public class USAStateOrWorldCountryDataGetter implements IDataGetter {

	@Override
	public <T extends ICanonicalCaseDeathData> List<T> getDataUsing(IExternalDataListGetter eds, RegionsInDashboard region) {
		Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
		log.info(this.getClass().getSimpleName() + " is about to call makeDataListFromExternalSource with " + eds.toString());
		return eds.makeDataListFromExternalSource(region.name());
	}
}
