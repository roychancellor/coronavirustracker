package com.royware.corona.dashboard.enums.charts;

public enum ChartCsvHeaders {
	CASES_TIME_SERIES("totalCases"),
	CASES_RATE("rateOfCases%"),
	CASES_ACCEL("accelCases%"),
	CASES_CHG_BY_CASES("chgCasesOverCases"),
	CASES_TOTAL_CURRENT("totalCurrentCases"),
	DEATHS_TIME_SERIES("totalDeaths"),
	DEATHS_RATE("rateOfDeaths%"),
	DEATHS_ACCEL("accelDeaths%"),
	DEATHS_CHG_BY_CASES("chgDeathsOverDeaths"),
	TESTS_TIME_SERIES("totalTests"),
	TESTS_RATIO("ratioPositivesToTests"),
	HOSP_CURRENT("hospCurrent"),
	HOSP_CUMULATIVE("hospCumulative");
//	CASES_TIME_SERIES("totalCases,dailyCases"),
//	CASES_RATE("rateOfCases,4dayMovAvgRateCases"),
//	CASES_ACCEL("accelCases,4DayMovAvgAccelCases"),
//	CASES_CHG_BY_CASES("chgCasesOverCases,expLine"),
//	DEATHS_TIME_SERIES("totalDeaths,dailyDeaths"),
//	DEATHS_RATE("rateOfDeaths,4dayMovAvgRateDeaths"),
//	DEATHS_ACCEL("accelDeaths,4dayMovAvgAccelDeaths"),
//	DEATHS_CHG_BY_CASES("chgDeathsOverDeaths,expLine"),
//	TESTS_TIME_SERIES("totalTests,4dayMovAvgNewTests"),
//	TESTS_RATIO("ratioPositivesToTests,4dayMovAvgTestRatio"),
//	HOSP_CURRENT("hospCurrent,4dayMovAvgNewHosp"),
//	HOSP_CUMULATIVE("hospCumulative,4dayMovAvgNewHosp");
	
	public final String headerName;
	
	private ChartCsvHeaders(String headerName) {
		this.headerName = headerName;
	}
	
	public String getName() {
		return this.headerName;
	}
}
