package com.royware.corona.dashboard.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.CanvasjsChartDao;
import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public class CanvasjsChartDaoImpl implements CanvasjsChartDao {
	@Autowired
	CanvasjsChartListBuilder chartBuilder;
	
	@Override
	public List<Map<Object, Object>> getCanvasjsChartData(UnitedStatesData[] usd) {
		return chartBuilder.getCanvasjsScatterLists(usd);
	}
 
}