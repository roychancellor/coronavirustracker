package com.royware.corona.dashboard.interfaces.data;

public interface IExternalDataServiceFactory {
	public IExternalDataConnectionService getExternalDataService(String typeOfService);
}
