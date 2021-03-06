package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

public interface IExternalDataConnectionService {
	public static final int US_CUTOFF_DATE = 20200304;
	
	public <T extends ICanonicalCaseDeathData> List<T> makeDataListFromExternalSource(String cacheKey);
}
