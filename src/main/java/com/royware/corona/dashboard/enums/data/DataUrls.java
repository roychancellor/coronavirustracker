package com.royware.corona.dashboard.enums.data;

public enum DataUrls {
	WORLD_DATA_URL_EUROCDC("https://opendata.ecdc.europa.eu/covid19/casedistribution/json/"),
	WORLD_DATA_URL_OWID("https://covid.ourworldindata.org/data/owid-covid-data.json"),
	STATE_DATA_URL_START("https://data.cdc.gov/resource/"),
	STATE_DATA_URL_CASES_DEATHS("9mfq-cb36"),
	STATE_DATA_URL_HOSPITAL_LOAD("g62h-syeh"),
	STATE_DATA_URL_TEST_RESULTS("NONE"),
	STATE_DATA_URL_END(".json?state="),
	COVID_ACT_NOW_API_KEY("?apiKey=049f81f31cd742e6a1aeaa84c8e9d24c"),
	US_DATA_URL("https://covidtracking.com/api/v1/us/daily.json");
	
	public final String url;
	
	private DataUrls(String url) {
		this.url = url;
	}
	
	public String getName() {
		return this.url;
	}
}
