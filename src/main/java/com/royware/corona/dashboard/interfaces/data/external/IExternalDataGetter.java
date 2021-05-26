package com.royware.corona.dashboard.interfaces.data.external;

import java.util.List;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public interface IExternalDataGetter {
	public <T extends ICanonicalCaseDeathData> List<T> getDataUsing(IExternalDataConnectionService eds, RegionsInDashboard region);
}
