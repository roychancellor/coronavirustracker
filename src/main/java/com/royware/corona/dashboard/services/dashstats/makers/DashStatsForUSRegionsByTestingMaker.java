package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigMakerUtilities;

public class DashStatsForUSRegionsByTestingMaker implements IDashStatsMaker {
	private static final Logger log = LoggerFactory.getLogger(DashStatsForUSRegionsByTestingMaker.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends CanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<C> chartData, int regionPop) {
		
		if(dashStats == null) {
			dashStats = new DashboardStatistics();
		}
		
		log.debug("Getting the region population from the Regions enum");
		int usaPop = RegionsData.USA.getRegionData().getPopulation();
		log.debug("Making total tests conducted");
		dashStats.setTotalTestsConducted(dataList.get(dataList.size() - 1).getTotalPositiveCases()
				+ dataList.get(dataList.size() - 1).getTotalNegativeCases());
		log.debug("Making ProportionOfPositiveTests and ProportionOfPositiveTestsMovingAverage");
		dashStats.setProportionOfPositiveTests(dataList.get(dataList.size() - 1).getTotalPositiveCases()
				* 100.0 / dashStats.getTotalTestsConducted());
		int totalTestsLastN = ChartConfigMakerUtilities.computeTotalQuantityLastN((List<Map<Object, Object>>) chartData, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue());
		dashStats.setTotalTestsConductedLastN(totalTestsLastN);
		dashStats.setProportionOfPositiveTestsMovingAverage(
				ChartConfigMakerUtilities.computeTotalQuantityLastN((List<Map<Object, Object>>) chartData, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())* 100.0 / totalTestsLastN);
		log.debug("Making ProportionOfPopulationTested");
		dashStats.setProportionOfPopulationTested(dashStats.getTotalTestsConducted()
				* 100.0 / usaPop);
		log.debug("Making ProportionOfDeathsFromTested");
		dashStats.setProportionOfDeathsFromTested(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dashStats.getTotalTestsConducted());
		log.debug("Making ProportionOfDeathsOfExtrapolatedCases");
		dashStats.setProportionOfDeathsOfExtrapolatedCases(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / (dashStats.getProportionOfPositiveTests() * usaPop));
		
		return dashStats;
	}
}