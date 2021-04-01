package com.royware.corona.dashboard.enums.data;

public enum DataUrls {
	WORLD_DATA_URL_EUROCDC("https://opendata.ecdc.europa.eu/covid19/casedistribution/json/"),
	WORLD_DATA_URL_OWID("https://covid.ourworldindata.org/data/owid-covid-data.json"),
	STATE_DATA_URL_CASES_DEATHS_START("https://data.cdc.gov/resource/"),
	STATE_DATA_URL_HOSPITAL_LOAD_START("https://healthdata.gov/resource/"),
	STATE_DATA_URL_CASE_DEATH_VACC_START("https://api.covidactnow.org/v2/state/"),
	STATE_DATA_URL_CASES_DEATHS_CDC("9mfq-cb36"),
	STATE_DATA_URL_HOSPITAL_LOAD_CDC("g62h-syeh"),
	STATE_DATA_URL_CASE_DEATH_VACC_FILE(".timeseries.json"),
	STATE_DATA_URL_END(".json?state="),
	STATE_DATA_URL_CASE_DEATH_VACC_END("?apiKey=049f81f31cd742e6a1aeaa84c8e9d24c");
	
	public final String url;
	
	private DataUrls(String url) {
		this.url = url;
	}
	
	public String getText() {
		return this.url;
	}
}
