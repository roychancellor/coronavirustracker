package com.royware.corona.dashboard.enums.data;

public enum DataUrls {
	WORLD_DATA_URL("https://opendata.ecdc.europa.eu/covid19/casedistribution/json/"),
	STATE_DATA_URL_START("https://covidtracking.com/api/v1/states/"),
	STATE_DATA_URL_END("/daily.json"),
	US_DATA_URL("https://covidtracking.com/api/v1/us/daily.json");
	
	public final String url;
	
	private DataUrls(String url) {
		this.url = url;
	}
	
	public String getName() {
		return this.url;
	}
}
