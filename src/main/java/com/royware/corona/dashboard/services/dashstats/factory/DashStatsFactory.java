package com.royware.corona.dashboard.services.dashstats.factory;

import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.dashstats.DashStatsTypes;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsByUSTotalsMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForRegionCasesMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForRegionCasesMovingSumMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForRegionDeathsMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForRegionDeathsMovingSumMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForRegionVaccMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForUSRegionsByTestingMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsForUSRegionsProportionsMaker;
import com.royware.corona.dashboard.services.dashstats.makers.DashStatsPerCapitaStatsMaker;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsFactory;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsMaker;

@Component
public class DashStatsFactory implements IDashStatsFactory {		
	@Override
	public IDashStatsMaker create(DashStatsTypes statsType) {		
		switch(statsType) {
			case DASHSTATS_BY_US_TOTALS: return new DashStatsByUSTotalsMaker();
			case DASHSTATS_REGION_CASES: return new DashStatsForRegionCasesMaker();
			case DASHSTATS_REGION_CASES_MOVING_SUM: return new DashStatsForRegionCasesMovingSumMaker();
			case DASHSTATS_REGION_DEATHS: return new DashStatsForRegionDeathsMaker();
			case DASHSTATS_REGION_DEATHS_MOVING_SUM: return new DashStatsForRegionDeathsMovingSumMaker();
			case DASHSTATS_REGION_VACC: return new DashStatsForRegionVaccMaker();
			case DASHSTATS_US_REGIONS_BY_TESTING: return new DashStatsForUSRegionsByTestingMaker();
			case DASHSTATS_US_REGIONS_PROPORTIONS: return new DashStatsForUSRegionsProportionsMaker();
			case DASHSTATS_PER_CAPITA_STATS: return new DashStatsPerCapitaStatsMaker();
			default: return null;
		}		
	}
}
