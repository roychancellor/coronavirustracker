package com.royware.corona.dashboard.services.chartlistfactory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.charts.IChartListFactory;
import com.royware.corona.dashboard.interfaces.charts.IChartListStore;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class ChartListStore implements IChartListStore {
	@Autowired
	private IChartListFactory chartListFactory;
	
	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> produceChartListFromRegionData(
			ChartTypes chartType,
			List<T> regionData,
			int regionPopulation) {
		
		return chartListFactory.create(chartType).makeListFrom(regionData, regionPopulation);
	}	
}
