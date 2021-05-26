package com.royware.corona.dashboard.interfaces.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Service
public interface IDashStatsMaker {
	public <T extends ICanonicalCaseDeathData> DashboardStatistics
		makeStats(
			DashboardStatistics dashStats,
			List<T> dataList,
			List<List<Map<Object, Object>>> chartData,
			int regionPop
		);
}