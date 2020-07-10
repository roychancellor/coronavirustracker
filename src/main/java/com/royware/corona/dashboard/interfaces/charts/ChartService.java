package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public interface ChartService {
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLine(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalDeathsVersusTimeWithExponentialFit(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfDeathsWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsWithExponentialLine(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyTestsTotalTestsVersusTime(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRatioCasesToTestsWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedNowWithMovingAverage(List<T> regionDataList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedTotalWithMovingAverage(List<T> regionDataList);
}
