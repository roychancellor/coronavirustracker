package com.royware.corona.dashboard.interfaces.data.external;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;

@Service
public interface IExternalDataGetterFactory {
	public IDataGetter create(RegionsInDashboard region);
}
