package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.ChartServiceDao;
import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartListService;

@Service
public class ChartServiceDaoImpl implements ChartServiceDao {
	@Autowired
	ChartListService chartListService;
	
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList(List<T> regionCaseList) {
		return chartListService.makeTotalCasesVersusTimeWithExponentialFitList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfCasesWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyRateOfChangeOfCasesWithMovingAverageList(regionCaseList);
	}
 
	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyAccelerationOfCasesWithMovingAverageList(List<T> regionCaseList) {
		return chartListService.makeDailyAccelerationOfCasesWithMovingAverageList(regionCaseList);
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalCasesVersusCaseswithExponentialLineList(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getChangeInTotalDeathsVersusDeathsswithExponentialLineList(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends CanonicalCases> List<List<Map<Object, Object>>> getDailyRateOfChangeOfDeathsWithMovingAverageList(
			List<T> regionCaseList) {
		// TODO Auto-generated method stub
		return null;
	}
}