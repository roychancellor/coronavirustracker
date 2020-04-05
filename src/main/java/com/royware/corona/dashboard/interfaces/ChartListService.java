package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ChartListService {
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> caseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> caseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsswithExponentialLineList(List<T> caseList);
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> caseList);
}
