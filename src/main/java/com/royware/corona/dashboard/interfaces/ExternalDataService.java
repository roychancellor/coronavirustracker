package com.royware.corona.dashboard.interfaces;

import java.util.List;

public interface ExternalDataService {
	public static final int US_CUTOFF_DATE = 20200304;
	public static final long CACHE_EVICT_PERIOD_MILLISECONDS = 3 * 60 * 60 * 1000;  //every 3 hours
	public static final String CACHE_NAME = "dataCache";
	public <T extends CanonicalCases> List<T> makeDataListFromExternalSource(String cacheKey);
}
