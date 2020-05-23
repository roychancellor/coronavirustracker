package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

public interface ExternalDataService {
	public static final int US_CUTOFF_DATE = 20200304;
	
	public <T extends CanonicalData> List<T> makeDataListFromExternalSource(String cacheKey);
}
