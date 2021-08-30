package com.royware.corona.dashboard.interfaces.data.external;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Service
public interface IDataGetterStore {
	public <T extends ICanonicalCaseDeathData> List<T> getDataFor(
			RegionsInDashboard region,
			IExternalDataListGetter usingExternalDataService);
}
