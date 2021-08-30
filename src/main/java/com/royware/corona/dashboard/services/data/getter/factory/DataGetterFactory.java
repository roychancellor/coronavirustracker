package com.royware.corona.dashboard.services.data.getter.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.data.external.IDataGetter;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterFactory;
import com.royware.corona.dashboard.services.data.getters.USADataGetter;
import com.royware.corona.dashboard.services.data.getters.USAStateOrWorldCountryDataGetter;
import com.royware.corona.dashboard.services.data.getters.USAWithoutNYDataGetter;

@Component
public class DataGetterFactory implements IExternalDataGetterFactory {		
	@Override
	public IDataGetter create(RegionsInDashboard region) {		
		switch(region) {
			case USA: return new USADataGetter();
			case USA_NO_NY: return new USAWithoutNYDataGetter();
			default: return new USAStateOrWorldCountryDataGetter();
		}		
	}
}
