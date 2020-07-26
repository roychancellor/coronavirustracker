package com.royware.corona.dashboard.services.charts;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.charts.ChartServiceListCreator;
import com.royware.corona.dashboard.interfaces.charts.ChartServiceDao;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;

@Service
public class ChartServiceDaoImpl implements ChartServiceDao {
	@Autowired
	ChartServiceListCreator chartListService;
	
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		return chartListService.makeDailyAndTotalCasesVersusTimeList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyRateOfChangeOfCasesWithMovingAverageList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyAccelerationOfCasesWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLineList(
			List<T> regionCaseList) {
		return chartListService.makeChangeInTotalCasesVersusCasesWithExponentialLineList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getTotalDeathsVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		return chartListService.makeTotalDeathsVersusTimeWithExponentialFitList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverageList(
			List<T> regionCaseList) {
		return chartListService.makeDailyRateOfChangeOfDeathsWithMovingAverageList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyAccelerationOfDeathsWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyAccelerationOfDeathsWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsWithExponentialLineList(
			List<T> regionCaseList) {
		return chartListService.makeChangeInTotalDeathsVersusDeathsWithExponentialLineList(regionCaseList);
	}
	
	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyTestsTotalTestsVersusTimeList(
			List<T> regionCaseList) {
		return chartListService.makeDailyTestsTotalTestsVersusTimeList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyRatioCasesToTestsWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyRatioCasesToTestsWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedNowWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyHospitalizedNowWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getDailyHospitalizedTotalWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyHospitalizedTotalWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalData> List<List<Map<Object, Object>>> getCurrentTotalPositivesWithMovingAverageList(List<T> regionDataList) {
		return chartListService.makeCurrentTotalPositivesWithMovingAverageList(regionDataList);
	}
}