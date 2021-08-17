package com.royware.corona.dashboard.services.chart.list.factory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListFactory;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListStore;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.services.chart.list.makers.ChartListMakerUtilities;

@Component
public class ChartListStore implements IChartListStore {
	@Autowired
	private IChartListFactory chartListFactory;
	
	@Autowired
	Environment config;
	
	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> produceChartListFromRegionData(
			ChartTypes chartType,
			List<T> regionData,
			int regionPopulation) {
		
		if(Boolean.parseBoolean(config.getProperty("corona.filter.data"))) {
			regionData = ChartListMakerUtilities.cleanDataList(regionData);
		}		
		
		List<List<Map<Object, Object>>> toReturn = chartListFactory.create(chartType).makeListFrom(regionData, regionPopulation);
		
		return toReturn;
	}	
}
