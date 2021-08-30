package com.royware.corona.dashboard.interfaces.data.external;

import java.util.List;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public interface IDataGetter {
	public <T extends ICanonicalCaseDeathData> List<T> getDataUsing(IExternalDataListGetter eds, RegionsInDashboard forRegion);
}
