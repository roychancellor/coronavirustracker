package com.royware.corona.dashboard.services.chartlistfactory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.charts.IChartList;
import com.royware.corona.dashboard.interfaces.charts.IChartListFactory;
import com.royware.corona.dashboard.services.chartlistmakers.ChangeInTotalCasesVersusCasesWithExponentialLineChartList;
import com.royware.corona.dashboard.services.chartlistmakers.ChangeInTotalDeathsVersusDeathsWithExponentialLineChartList;
import com.royware.corona.dashboard.services.chartlistmakers.CurrentTotalDeathsWithPercentOfPopulationChartList;
import com.royware.corona.dashboard.services.chartlistmakers.CurrentTotalPositivesWithPercentOfPopulationChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyAccelerationOfCasesWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyAccelerationOfDeathsWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyAndTotalCasesVersusTimeChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyHospitalizedNowWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyHospitalizedTotalWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyRateOfChangeOfCasesWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyRateOfChangeOfDeathsWithMovingAverageChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyTestsTotalTestsVersusTimeChartList;
import com.royware.corona.dashboard.services.chartlistmakers.DailyVaccTotalVaccVersusTimeChartList;
import com.royware.corona.dashboard.services.chartlistmakers.TotalDeathsVersusTimeWithExponentialFitChartList;

@Component
public class ChartListFactory implements IChartListFactory {		
	@Override
	public IChartList create(ChartTypes chartType) {		
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
