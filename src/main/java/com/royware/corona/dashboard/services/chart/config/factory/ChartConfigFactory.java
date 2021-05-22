package com.royware.corona.dashboard.services.chart.config.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigFactory;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigAccelerationOfCases;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigAccelerationOfDeaths;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigCasesByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigCumulativeHospitalizationsByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigCurrentHospitalizationsByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigDeathsByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigRateOfCasesVersusCases;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigRateOfChangeOfCases;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigRateOfChangeOfDeaths;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigRateOfDeathsVersusDeaths;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigRatioOfCasesToTestsByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigTestsByTime;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigTotalCurrentCases;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigVaccByTime;

@Component
public class ChartConfigFactory implements IChartConfigFactory {		
	@Override
	public IChartConfigMaker create(ChartTypes chartType) {		
		switch(chartType) {
			case CASES_DAILY_AND_TOTAL_VERSUS_TIME: return new ChartConfigCasesByTime();
			case CASES_RATE_OF_CHANGE_VERSUS_TIME: return new ChartConfigRateOfChangeOfCases();
			case CASES_ACCELERATION_VERSUS_TIME: return new ChartConfigAccelerationOfCases();
			case CASES_CHANGE_VERSUS_CASES: return new ChartConfigRateOfCasesVersusCases();
			case CASES_AS_FRAC_OF_POP_VERSUS_TIME: return new ChartConfigTotalCurrentCases();
			case CASES_TO_TESTS_RATIO_VERSUS_TIME: return new ChartConfigRatioOfCasesToTestsByTime();
			case DEATHS_DAILY_AND_TOTAL_VERSUS_TIME: return new ChartConfigDeathsByTime();
			case DEATHS_RATE_OF_CHANGE_VERSUS_TIME: return new ChartConfigRateOfChangeOfDeaths();
			case DEATHS_ACCELERATION_VERSUS_TIME: return new ChartConfigAccelerationOfDeaths();
			case DEATHS_CHANGE_VERSUS_DEATHS: return new ChartConfigRateOfDeathsVersusDeaths();
			//case DEATHS_AS_FRAC_OF_POP_VERSUS_TIME: return new Chart;
			case HOSP_NOW_VERSUS_TIME: return new ChartConfigCurrentHospitalizationsByTime();
			case HOSP_TOTAL_VERSUS_TIME: return new ChartConfigCumulativeHospitalizationsByTime();
			case VACC_DAILY_AND_TOTAL_VERSUS_TIME: return new ChartConfigVaccByTime();
			case TESTS_DAILY_AND_TOTAL_VERSUS_TIME: return new ChartConfigTestsByTime();
			default: return null;
		}		
	}
}
