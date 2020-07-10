package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public interface ChartServiceListCreator {
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAndTotalCasesVersusTimeList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCasesWithExponentialLineList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeTotalDeathsVersusTimeWithExponentialFitList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfDeathsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyTestsTotalTestsVersusTimeList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRatioCasesToTestsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedNowWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyHospitalizedTotalWithMovingAverageList(List<T> regionDataList);
}
