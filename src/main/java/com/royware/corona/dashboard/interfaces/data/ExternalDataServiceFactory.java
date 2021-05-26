package com.royware.corona.dashboard.interfaces.data;

public interface ExternalDataServiceFactory {
	public IExternalDataConnectionService getExternalDataService(String typeOfService);
}
