package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigMakerUtilities;

public class DashStatsForRegionVaccMaker implements IDashStatsMaker {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<C> chartData, int regionPop) {
		
		if(dashStats == null) {
			dashStats = new DashboardStatistics();
		}
		
		List<Map<Object, Object>> chartDataList = (List<Map<Object, Object>>) chartData.get(0);
		dashStats.setTotalVaccCompleted((int) chartData.get(0).get(chartData.get(0).size() - 1).get("y"));
		dashStats.setVaccToday((int) chartData.get(0).get(chartData.get(0).size() - 1).get("y")
				- (int) chartData.get(0).get(chartData.get(0).size() - 2).get("y"));
		dashStats.setVaccMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPop);
		dashStats.setVaccMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPop);
		
		return dashStats;
	}

}
