package com.royware.corona.dashboard.enums.charts;

public enum ChartCsvHeaders {
	CASES_TIME_SERIES("totalPositives"),
	CASES_RATE("dailyRateOfPositives%"),
	CASES_ACCEL("dailyAccelCases%"),
	CASES_CHG_BY_CASES("chgCasesOverCases"),
	CASES_TOTAL_CURRENT("positivityPer100K"),
	DEATHS_TIME_SERIES("totalDeaths"),
	DEATHS_RATE("dailyRateOfDeaths%"),
	DEATHS_ACCEL("dailyAccelDeaths%"),
	DEATHS_CHG_BY_CASES("chgDeathsOverDeaths"),
	TESTS_TIME_SERIES("totalTestsConducted"),
	TESTS_RATIO("ratioDailyPositivesToDailyTests"),
	VACC_TIME_SERIES("totalVaccConducted"),
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
