package com.royware.corona.dashboard.interfaces.data.external;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Service
public interface IExternalDataGetterStore {
	public <T extends ICanonicalCaseDeathData> List<T> getDataFromExternalSource(
			String region,
			boolean cleanNegativeChangesFromTotals);
}
