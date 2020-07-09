package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public interface ChartServiceListCreator {
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAndTotalCasesVersusTimeList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCasesWithExponentialLineList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeTotalDeathsVersusTimeWithExponentialFitList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfDeathsWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyTestsTotalTestsVersusTimeList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRatioCasesToTestsWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedNowWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedTotalWithMovingAverageList(List<T> regionCaseList);
}
