package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

public class DashStatsForUSRegionsProportionsMaker implements IDashStatsMaker {
	private static final Logger log = LoggerFactory.getLogger(DashStatsForUSRegionsProportionsMaker.class);
	
	@Override
	public <T extends ICanonicalCaseDeathData> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<List<Map<Object, Object>>> chartData, int regionPop) {
		
		if(dashStats == null) {
			dashStats = new DashboardStatistics();
		}
		
		log.debug("Getting the region population from the Regions enum");
		int usaPop = RegionsInDashboard.USA.getPopulation();
		log.debug("Making ProportionOfDeathsFromPositives");
		dashStats.setProportionOfDeathsFromPositives(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dataList.get(dataList.size() - 1).getTotalPositiveCases());
		log.debug("Making ProportionOfPopulationVaccinated");
		dashStats.setProportionOfPopulationVaccinated(dashStats.getTotalVaccCompleted()
				* 100.0 / usaPop);
		
		return dashStats;
	}
}
