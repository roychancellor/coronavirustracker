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
	ChartListService chartService;
	
	@Override
	public <T> List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList(CanonicalCases cases) {
		return chartService.makeTotalCasesVersusTimeWithExponentialFitList(cases);
	}
 
}