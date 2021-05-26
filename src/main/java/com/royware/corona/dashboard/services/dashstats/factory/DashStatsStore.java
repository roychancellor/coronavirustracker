package com.royware.corona.dashboard.services.dashstats.factory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.dashstats.DashStatsTypes;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsFactory;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsStore;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Component
public class DashStatsStore implements IDashStatsStore {
	@Autowired
	private IDashStatsFactory dashStatsFactory;
	
	@Override
	public <T extends ICanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics
		produceDashboardStatsForType(
			DashStatsTypes statsType,
			DashboardStatistics dashStats,
			List<T> dataList,
			List<C> chartData,
			int regionPop) {
		
		return dashStatsFactory.create(statsType).makeStats(dashStats, dataList, chartData, regionPop);
	}	
}
