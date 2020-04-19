package com.royware.corona.dashboard.enums;

public enum DataUrls {
	WORLD_DATA_URL("https://opendata.ecdc.europa.eu/covid19/casedistribution/json/"),
	STATE_DATA_URL("https://covidtracking.com/api/states/daily?state="),
	US_DATA_URL("https://covidtracking.com/api/us/daily");
	
	public final String name;
	
	private DataUrls(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
