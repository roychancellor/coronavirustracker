package com.royware.corona.dashboard.interfaces.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Service
public interface DashboardChartService {
	public <T extends CanonicalCaseDeathData> List<DashboardChart> makeAllDashboardChartsAndStats(
			List<T> caseList, String region, Integer regionPopulation, DashboardStatistics dashStats);	
}
