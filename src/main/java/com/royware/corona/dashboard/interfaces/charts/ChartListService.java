package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public interface ChartListService {
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeTotalCasesVersusTimeWithExponentialFitList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfCasesWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfCasesWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalCasesVersusCaseswithExponentialLineList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeTotalDeathsVersusTimeWithExponentialFitList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyRateOfChangeOfDeathsWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeDailyAccelerationOfDeathsWithMovingAverageList(List<T> caseList);
	public <T extends CanonicalData> List<List<Map<Object, Object>>> makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(List<T> caseList);
}