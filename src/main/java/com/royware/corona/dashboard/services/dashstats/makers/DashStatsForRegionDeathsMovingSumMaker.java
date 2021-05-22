package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigMakerUtilities;

public class DashStatsForRegionDeathsMovingSumMaker implements IDashStatsMaker {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<C> chartData, int regionPop) {
		
		if(dashStats == null) {
			dashStats = new DashboardStatistics();
		}
		
		List<Map<Object, Object>> chartDataList = (List<Map<Object, Object>>) chartData.get(0);
		dashStats.setDeathsMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPop);
		dashStats.setDeathsMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPop);
		
		return dashStats;
	}

}
