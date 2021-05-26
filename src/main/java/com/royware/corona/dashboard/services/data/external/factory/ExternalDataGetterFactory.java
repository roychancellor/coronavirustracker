package com.royware.corona.dashboard.services.data.external.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.services.data.external.getters.USADataGetter;
import com.royware.corona.dashboard.services.data.external.getters.USAStateOrWorldCountryDataGetter;
import com.royware.corona.dashboard.services.data.external.getters.USAWithoutNYDataGetter;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetter;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterFactory;

@Component
public class ExternalDataGetterFactory implements IExternalDataGetterFactory {		
	@Override
	public IExternalDataGetter create(RegionsInDashboard region) {		
		switch(region) {
			case USA: return new USADataGetter();
			case USA_NO_NY: return new USAWithoutNYDataGetter();
			default: return new USAStateOrWorldCountryDataGetter();
		}		
	}
}
