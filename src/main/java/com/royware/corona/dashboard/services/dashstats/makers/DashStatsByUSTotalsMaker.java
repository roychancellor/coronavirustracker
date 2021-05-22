package com.royware.corona.dashboard.services.dashstats.makers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

public class DashStatsByUSTotalsMaker implements IDashStatsMaker {
	private static final Logger log = LoggerFactory.getLogger(DashStatsByUSTotalsMaker.class);
	
	@Override
	public <T extends CanonicalCaseDeathData, C extends List<M>, M extends Map<Object, Object>> DashboardStatistics makeStats(
			DashboardStatistics dashStats, List<T> dataList, List<C> chartData, int regionPop) {
		
		int totalUSCases = dataList.get(dataList.size() - 1).getTotalPositiveCases();
		log.debug("Making totalUSCases");
		dashStats.setTotalUsCases(totalUSCases);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionCasesToUsCases(dashStats.getCasesTotal() * 100.0 / totalUSCases);
		int totalUSDeaths = dataList.get(dataList.size() - 1).getTotalDeaths();
		log.debug("Making totalUSDeaths");
		dashStats.setTotalUsDeaths(totalUSDeaths);
		log.debug("Making proportionOfRegionDeathsToUsCases");
		dashStats.setProportionOfRegionDeathsToUsDeaths(dashStats.getDeathsTotal() * 100.0 / totalUSDeaths);
		log.debug("Making proportionOfRegionPopToUsPop");
		dashStats.setProportionOfRegionPopToUsPop(regionPop * 100.0 / RegionsData.USA.getRegionData().getPopulation());
		int totalUSVacc = dataList.get(dataList.size() - 1).getTotalVaccCompleted();
		log.debug("Making totalUSVacc");
		dashStats.setTotalUsVacc(totalUSVacc);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionVaccToUsVacc(dashStats.getTotalVaccCompleted() * 100.0 / totalUSVacc);
		
		return dashStats;
	}

}
