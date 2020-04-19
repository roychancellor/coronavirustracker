package com.royware.corona.dashboard.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.DashboardStatistics;

@Service
public interface DashboardConfigService {
	public <T extends CanonicalCases> List<Dashboard> makeAllDashboardCharts(List<T> caseList, String region, DashboardStatistics dashStats);
}
