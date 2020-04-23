package com.royware.corona.dashboard.interfaces;

import java.util.List;

public interface ExternalDataService {
	public static final int US_CUTOFF_DATE = 20200304;
	
	public <T extends CanonicalCases> List<T> makeDataListFromExternalSource(String cacheKey);
}
