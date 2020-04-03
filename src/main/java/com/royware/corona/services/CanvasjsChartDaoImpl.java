package com.royware.corona.services;

import java.util.List;
import java.util.Map;

import com.royware.corona.interfaces.CanvasjsChartDao;
import com.royware.corona.model.CanvasjsChartData;

public class CanvasjsChartDaoImpl implements CanvasjsChartDao {
	 
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return CanvasjsChartData.getCanvasjsDataList();
	}
 
}