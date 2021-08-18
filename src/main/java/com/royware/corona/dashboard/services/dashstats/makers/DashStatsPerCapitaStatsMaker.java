package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.ChartListConstants;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

public class DashStatsPerCapitaStatsMaker implements IDashStatsMaker {
	
	@Override
	public <T extends ICanonicalCaseDeathData> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<List<Map<Object, Object>>> chartData, int regionPop) {
		
		if(dashStats == null) {
			dashStats = new DashboardStatistics();
		}
		
		dashStats.setCasesPerCapita(dashStats.getCasesTotal() * 1.0 * ChartListConstants.PER_CAPITA_BASIS.getValue() / regionPop);
		dashStats.setCasesPercentOfPop(dashStats.getCasesTotal() * 100.0 / regionPop);
		dashStats.setDeathsPerCapita(dashStats.getDeathsTotal() * 1.0 * ChartListConstants.PER_CAPITA_BASIS.getValue() / regionPop);
		dashStats.setDeathsPercentOfPop(dashStats.getDeathsTotal() * 100.0 / regionPop);
		dashStats.setVaccPerCapita(dashStats.getTotalVaccCompleted() * 1.0 * ChartListConstants.PER_CAPITA_BASIS.getValue() / regionPop);
		dashStats.setVaccPercentOfPop(dashStats.getTotalVaccCompleted() * 100.0 / regionPop);
		
		return dashStats;
	}
}
