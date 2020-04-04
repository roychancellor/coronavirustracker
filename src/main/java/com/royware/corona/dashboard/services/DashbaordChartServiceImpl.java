package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.DashboardChartDao;
import com.royware.corona.dashboard.interfaces.DashboardChartService;

@Service
public class DashbaordChartServiceImpl implements DashboardChartService {
	@Autowired
	private DashboardChartDao canvasjsChartDao;
 
	public void setCanvasjsChartDao(DashboardChartDao canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit() {
		return canvasjsChartDao.getTotalCasesVersusTimeWithExponentialFitList();
	}
 
}