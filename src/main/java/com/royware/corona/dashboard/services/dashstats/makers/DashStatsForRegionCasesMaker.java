package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

public class DashStatsForRegionCasesMaker implements IDashStatsMaker {

	@Override
	public <T extends CanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<C> chartData, int regionPop) {
		
		dashStats.setCasesTotal((int) chartData.get(0).get(chartData.get(0).size() - 1).get("y"));
		dashStats.setCasesToday((int) chartData.get(0).get(chartData.get(0).size() - 1).get("y")
				- (int) chartData.get(0).get(chartData.get(0).size() - 2).get("y"));
		
		return dashStats;
	}

}
