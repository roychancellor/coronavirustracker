package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ChartServiceDao {
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> regionCaseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsswithExponentialLineList(List<T> regionCaseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> regionCaseList);
}
