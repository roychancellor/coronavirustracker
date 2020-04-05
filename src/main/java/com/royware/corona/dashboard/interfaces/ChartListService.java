package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ChartListService {
	public <T> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(CanonicalCases T);
	public <T> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(CanonicalCases T);
	public <T> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(CanonicalCases T);
	public <T> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList(CanonicalCases T);
	public <T> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsswithExponentialLineList(CanonicalCases T);
	public <T> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(CanonicalCases T);
}
