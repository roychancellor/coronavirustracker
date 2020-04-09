package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.ChartServiceDao;
import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartService;

@Service
public class ChartServiceImpl implements ChartService {
	@Autowired
	private ChartServiceDao serviceDao;
 
	public void setCanvasjsChartDao(ChartServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}
 
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit(List<T> regionCaseList) {
		return serviceDao.getTotalCasesVersusTimeWithExponentialFitList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyRateOfChangeOfCasesWithMovingAverageList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverage(List<T> regionCaseList) {
		return serviceDao.getDailyAccelerationOfCasesWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLine(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsswithExponentialLine(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverage(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}
}