package com.royware.corona.dashboard.interfaces.data.external;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Service
public interface IExternalDataGetterStore {
	public <T extends CanonicalCaseDeathData> List<T> getDataFor(
			RegionsInDashboard region,
			IExternalDataConnectionService externalDataService);
}
