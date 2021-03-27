package com.royware.corona.dashboard.services.charts;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.charts.ChartService;
import com.royware.corona.dashboard.interfaces.charts.ChartServiceDao;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Service
public class ChartServiceImpl implements ChartService {
	@Autowired
	private ChartServiceDao serviceDao;
 
	public void setCanvasjsChartDao(ChartServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}
 
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit(List<T> regionCaseList) {
		return serviceDao.getTotalCasesVersusTimeWithExponentialFitList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyRateOfChangeOfCasesWithMovingAverageList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyAccelerationOfCasesWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLine(
			List<T> regionCaseList) {
		return serviceDao.getChangeInTotalCasesVersusCaseswithExponentialLineList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getTotalDeathsVersusTimeWithExponentialFit(List<T> regionCaseList) {
		return serviceDao.getTotalDeathsVersusTimeWithExponentialFitList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverage(
			List<T> regionCaseList) {
		return serviceDao.getDailyRateOfChangeOfDeathsWithMovingAverageList(regionCaseList);
	}
	
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyAccelerationOfDeathsWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyAccelerationOfDeathsWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsWithExponentialLine(
			List<T> regionCaseList) {
		return serviceDao.getChangeInTotalDeathsVersusDeathsWithExponentialLineList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyTestsTotalTestsVersusTime(List<T> regionCaseList) {
		return serviceDao.getDailyTestsTotalTestsVersusTimeList(regionCaseList);
	}
	 
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyRatioCasesToTestsWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyRatioCasesToTestsWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyHospitalizedNowWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyHospitalizedNowWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyHospitalizedTotalWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyHospitalizedTotalWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getCurrentTotalPositivesWithPercentOfPopulation(List<T> regionDataList,
			Integer regionPopulation) {
		return serviceDao.getCurrentTotalPositivesWithPercentOfPopulationList(regionDataList, regionPopulation);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getCurrentTotalDeathsWithPercentOfPopulation(
			List<T> regionDataList, Integer regionPopulation) {
		return serviceDao.getCurrentTotalDeathsWithPercentOfPopulationList(regionDataList, regionPopulation);
	}

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> getDailyVaccTotalVaccVersusTime(List<T> regionDataList) {
		return serviceDao.getDailyTestsTotalTestsVersusTimeList(regionDataList);
	}
}