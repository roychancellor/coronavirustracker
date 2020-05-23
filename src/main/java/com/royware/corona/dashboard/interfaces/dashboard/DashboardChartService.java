package com.royware.corona.dashboard.interfaces.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.DashboardStatistics;

@Service
public interface DashboardChartService {
	public <T extends CanonicalData> List<Dashboard> makeAllDashboardCharts(
			List<T> caseList, String region, Integer regionPopulation, DashboardStatistics dashStats);
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats);
}
