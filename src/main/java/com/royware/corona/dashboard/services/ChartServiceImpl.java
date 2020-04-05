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
	private ChartServiceDao canvasjsChartDao;
 
	public void setCanvasjsChartDao(ChartServiceDao canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit() {
		return canvasjsChartDao.getTotalCasesVersusTimeWithExponentialFitList();
	}
 
}