package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public interface ChartServiceDao {
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalDeathsVersusTimeWithExponentialFitList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfDeathsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyTestsTotalTestsVersusTimeList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRatioCasesToTestsWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedNowWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedTotalWithMovingAverageList(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getCurrentTotalPositivesWithMovingAverageList(List<T> regionDataList);
}
