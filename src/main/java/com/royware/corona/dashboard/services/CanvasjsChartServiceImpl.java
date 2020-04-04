package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.CanvasjsChartDao;
import com.royware.corona.dashboard.interfaces.CanvasjsChartService;
import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public class CanvasjsChartServiceImpl implements CanvasjsChartService {
	 
	@Autowired
	private CanvasjsChartDao canvasjsChartDao;
 
	public void setCanvasjsChartDao(CanvasjsChartDao canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData(UnitedStatesData[] usd) {
		return canvasjsChartDao.getCanvasjsChartData(usd);
	}
 
}