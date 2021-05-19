package com.royware.corona.dashboard.services.chart.config.factory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigFactory;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigStore;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

@Component
public class ChartConfigStore implements IChartConfigStore {
	@Autowired
	private IChartConfigFactory chartConfigFactory;
	
	@Override
	public DashboardChartConfig produceChartConfigFromList(
			ChartTypes type,
			List<List<Map<Object, Object>>> chartList,
			String region) {
		
		return chartConfigFactory.create(type).makeConfigFrom(chartList, region);
	}	
}
