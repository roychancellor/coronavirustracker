package com.royware.corona.dashboard.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.DashboardStatistics;

@Service
public interface DashboardConfigService {
	public boolean populateDashboardModelMap(String region, ModelMap map);
	public <T extends CanonicalData> List<Dashboard> makeAllDashboardCharts(List<T> caseList, String region, DashboardStatistics dashStats);
}
