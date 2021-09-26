package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public interface IExternalDataListGetter {
	public <T extends ICanonicalCaseDeathData> List<T> makeDataListFromExternalSource(String cacheKey);
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals);
}
