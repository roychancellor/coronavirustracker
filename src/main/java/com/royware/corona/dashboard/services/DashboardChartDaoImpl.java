package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.DashboardChartDao;
import com.royware.corona.dashboard.interfaces.DashboardChartListBuilder;

@Service
public class DashboardChartDaoImpl implements DashboardChartDao {
	@Autowired
	DashboardChartListBuilder chartBuilder;
	
	@Override
	public List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFitList() {
		return chartBuilder.makeTotalCasesVersusTimeWithExponentialFitList();
	}
 
}