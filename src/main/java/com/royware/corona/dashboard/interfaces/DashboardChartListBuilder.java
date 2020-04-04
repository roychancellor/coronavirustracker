package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashboardChartListBuilder {
	public List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList();
	public List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList();
	public List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList();
	public List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList();
	public List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsswithExponentialLineList();
	public List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList();
}
