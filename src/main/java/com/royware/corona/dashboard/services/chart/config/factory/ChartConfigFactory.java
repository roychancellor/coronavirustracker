package com.royware.corona.dashboard.services.chart.config.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.charts.IChartListMaker;
import com.royware.corona.dashboard.services.chart.list.makers.ChangeInTotalCasesVersusCasesWithExponentialLineChartList;
import com.royware.corona.dashboard.services.chart.list.makers.ChangeInTotalDeathsVersusDeathsWithExponentialLineChartList;
import com.royware.corona.dashboard.services.chart.list.makers.CurrentTotalDeathsWithPercentOfPopulationChartList;
import com.royware.corona.dashboard.services.chart.list.makers.CurrentTotalPositivesWithPercentOfPopulationChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyAccelerationOfCasesWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyAccelerationOfDeathsWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyAndTotalCasesVersusTimeChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyHospitalizedNowWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyHospitalizedTotalWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyRateOfChangeOfCasesWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyRateOfChangeOfDeathsWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyTestsTotalTestsVersusTimeChartList;
import com.royware.corona.dashboard.services.chart.list.makers.DailyVaccTotalVaccVersusTimeChartList;
import com.royware.corona.dashboard.services.chart.list.makers.TotalDeathsVersusTimeWithExponentialFitChartList;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigFactory;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigMaker;
import com.royware.corona.dashboard.interfaces.charts.IChartListFactory;

@Component
public class ChartConfigFactory implements IChartConfigFactory {		
	@Override
	public IChartConfigMaker create(ChartTypes chartType) {		
		switch(chartType) {
			case CASES_DAILY_AND_TOTAL_VERSUS_TIME: return new DailyAndTotalCasesVersusTimeChartList();
			case CASES_RATE_OF_CHANGE_VERSUS_TIME: return new DailyRateOfChangeOfCasesWithMovingAverageChartList();
			case CASES_ACCELERATION_VERSUS_TIME: return new DailyAccelerationOfCasesWithMovingAverageChartList();
			case CASES_CHANGE_VERSUS_CASES: return new ChangeInTotalCasesVersusCasesWithExponentialLineChartList();
			case CASES_AS_FRAC_OF_POP_VERSUS_TIME: return new CurrentTotalPositivesWithPercentOfPopulationChartList();
			case CASES_TO_TESTS_RATIO_VERSUS_TIME: return new CurrentTotalPositivesWithPercentOfPopulationChartList();
			case DEATHS_DAILY_AND_TOTAL_VERSUS_TIME: return new TotalDeathsVersusTimeWithExponentialFitChartList();
			case DEATHS_RATE_OF_CHANGE_VERSUS_TIME: return new DailyRateOfChangeOfDeathsWithMovingAverageChartList();
			case DEATHS_ACCELERATION_VERSUS_TIME: return new DailyAccelerationOfDeathsWithMovingAverageChartList();
			case DEATHS_CHANGE_VERSUS_DEATHS: return new ChangeInTotalDeathsVersusDeathsWithExponentialLineChartList();
			case DEATHS_AS_FRAC_OF_POP_VERSUS_TIME: return new CurrentTotalDeathsWithPercentOfPopulationChartList();
			case HOSP_NOW_VERSUS_TIME: return new DailyHospitalizedNowWithMovingAverageChartList();
			case HOSP_TOTAL_VERSUS_TIME: return new DailyHospitalizedTotalWithMovingAverageChartList();
			case VACC_DAILY_AND_TOTAL_VERSUS_TIME: return new DailyVaccTotalVaccVersusTimeChartList();
			case TESTS_DAILY_AND_TOTAL_VERSUS_TIME: return new DailyTestsTotalTestsVersusTimeChartList();
			default: return null;
		}		
	}
}
