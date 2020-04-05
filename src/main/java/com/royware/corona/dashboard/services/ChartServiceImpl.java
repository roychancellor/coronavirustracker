package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.ChartServiceDao;
import com.royware.corona.dashboard.interfaces.ChartService;

@Service
public class ChartServiceImpl implements ChartService {
	@Autowired
	private ChartServiceDao serviceDao;
 
	public void setCanvasjsChartDao(ChartServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit() {
		return serviceDao.getTotalCasesVersusTimeWithExponentialFitList();
	}
 
}