package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

@Service
public interface IChartConfigStore {
	public DashboardChartConfig produceChartConfigFromList(
			ChartTypes type,
			List<List<Map<Object, Object>>> chartList,
			String forRegion);
}
