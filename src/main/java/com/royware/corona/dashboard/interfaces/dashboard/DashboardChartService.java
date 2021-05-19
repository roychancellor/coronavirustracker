package com.royware.corona.dashboard.interfaces.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Service
public interface DashboardChartService {
	public <T extends CanonicalCaseDeathData> List<DashboardChart> makeAllDashboardCharts(
			List<T> caseList, String region, Integer regionPopulation, DashboardStatistics dashStats);
	
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats);
	
	public void makeDashboardStatsForRegion(DashboardStatistics dashStats, int regionPopulation,
			List<List<Map<Object, Object>>> chartDataCasesByTime,
			List<List<Map<Object, Object>>> chartDataCasesMovingSum,
			List<List<Map<Object, Object>>> chartDataDeathsByTime,
			List<List<Map<Object, Object>>> chartDataVaccByTime);
	
	public <T extends CanonicalCaseDeathData> void makeDashboardStatsForUSRegionsByTesting(List<T> dataList, DashboardStatistics dashStats);	
}
