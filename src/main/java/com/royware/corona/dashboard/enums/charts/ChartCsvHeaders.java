package com.royware.corona.dashboard.enums.charts;

public enum ChartCsvHeaders {
	CASES_TIME_SERIES("cases"),
	CASES_RATE("rateOfCases"),
	CASES_ACCEL("accelOfCases"),
	CASES_CHG_BY_CASES("chgCasesOverCases"),
	DEATHS_TIME_SERIES("deaths"),
	DEATHS_RATE("rateOfDeaths"),
	DEATHS_ACCEL("accelOfDeaths"),
	DEATHS_CHG_BY_CASES("chgDeathsOverDeaths"),
	TESTS_TIME_SERIES("tests"),
	TESTS_RATIO("ratioPositvesToTests"),
	HOSP_CURRENT("hospCurrent"),
	HOSP_CUMULATIVE("hospCumulative");
	
	public final String headerName;
	
	private ChartCsvHeaders(String headerName) {
		this.headerName = headerName;
	}
	
	public String getName() {
		return this.headerName;
	}
}
