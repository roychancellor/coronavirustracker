package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.royware.corona.dashboard.enums.dashstats.DashStatsTypes;
import com.royware.corona.dashboard.enums.data.ChartListConstants;
import com.royware.corona.dashboard.enums.regions.RegionsInDashboard;
import com.royware.corona.dashboard.interfaces.dashboard.IDashboardChartService;
import com.royware.corona.dashboard.interfaces.dashboard.IDashboardConfigService;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsStore;
import com.royware.corona.dashboard.interfaces.data.IExternalDataConnectionService;
import com.royware.corona.dashboard.interfaces.data.IExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionExternalDataService;
import com.royware.corona.dashboard.interfaces.data.external.IExternalDataGetterStore;
import com.royware.corona.dashboard.interfaces.data.external.IRegionDemographicDataGetterStore;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardHeader;
import com.royware.corona.dashboard.model.dashboard.DashboardMeta;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

@Component
public class DashboardConfigServiceImpl implements IDashboardConfigService {
	@Autowired private IDashStatsStore dashStatsStore;
	@Autowired private IExternalDataServiceFactory dataFactory;
	@Autowired private DashboardMeta dashMeta;
	@Autowired private DashboardHeader dashHeader;
	@Autowired private DashboardStatistics dashStats;
	@Autowired private IDashboardChartService dashboardChartService;
	@Autowired private IMultiRegionExternalDataService dashboardMultiRegionService;
	@Autowired private IRegionDemographicDataGetterStore regionDemoDataStore;
	@Autowired private IExternalDataGetterStore externalDataGetterStore;
	@Autowired private Environment appConfig;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardConfigServiceImpl.class);
	
	@Override
	public boolean populateDashboardModelMap(String rawRegionString, ModelMap map) {
		List<? extends ICanonicalCaseDeathData> dataList = new ArrayList<>();
		String fullRegionString;
		int regionPopulation;
		boolean isMultiRegion = rawRegionString.length() > 3 ? rawRegionString.substring(0,5).equalsIgnoreCase("MULTI") : false;
		final int MAX_REGION_LENGTH_TO_DISPLAY = 28;
		
		//Check for null data service or an empty multi-region
		IExternalDataConnectionService dataService = getExternalDataServiceFromFactory(rawRegionString);
		if(dataService == null || (isMultiRegion && rawRegionString.substring(6).length() < 2)) {
			log.error("In populateDashboardModelMap: Unable to getExternalDataServiceFromFactory");
			return false;
		}
		
		//////// GET ALL DATA FROM EXTERNAL SOURCE(S) ////////
		//Need to get the data differently for a multi-region selection 
		//As of 03/07/2021, there is no longer a single source of data available for the U.S., so need to treat it like a multi-region
		boolean toValueOfAppConfigCleanData = Boolean.parseBoolean(appConfig.getProperty("corona.filter.data"));
		if(isMultiRegion) {
			fullRegionString = rawRegionString;
			String regionsOnlyCsvString = dashboardMultiRegionService.getStatesFromMultiRegionString(rawRegionString);
			regionPopulation = dashboardMultiRegionService.getMultiRegionPopulation(regionsOnlyCsvString);
			dataService.setCleanNegativeChangesFromTotals(toValueOfAppConfigCleanData);
			dataList = dashboardMultiRegionService.getMultiRegionDataFromExternalSource(regionsOnlyCsvString, dataService);
		} else {
			RegionsInDashboard region = RegionsInDashboard.valueOfEnum(rawRegionString);
			fullRegionString = regionDemoDataStore.getRegionFullNameFor(region);
			regionPopulation = regionDemoDataStore.getRegionPopulationFor(region);
			dataService.setCleanNegativeChangesFromTotals(toValueOfAppConfigCleanData);
			dataList = externalDataGetterStore.getDataFor(region, dataService);
		}
		log.debug("fullRegionString: " + fullRegionString + ", regionPopulation: " + regionPopulation + ", dataList size: " + dataList.size());
		log.info("Finished making the data list...");
		
		//Check for a null or empty data list. This is VERY important!!!
		if(dataList == null || dataList.isEmpty()) {
			log.error("In populateDashboardModelMap: The dataList is null or empty");
			return false;
		}
		
		//////// MAKE ALL DASHBOARD CHART DATA LISTS, CONFIGS, and STATS ////////
		log.info("About to call makeAllDashboardCharts with region = " + fullRegionString);
		map.addAttribute("allDashboardCharts", dashboardChartService.makeAllDashboardChartsAndStats(dataList, fullRegionString, regionPopulation, dashStats));
		log.info("Done calling makeAllDashboardCharts");
		
		//This setting determines whether the last row of the statistics table will show
		boolean populateUSTotals = true;
		dashMeta.setRegionType("us");
		if(rawRegionString.length() == 3 && !rawRegionString.equalsIgnoreCase("USA")) {
			dashMeta.setRegionType("world");
		} else if((rawRegionString.length() == 2 || isMultiRegion) && populateUSTotals) {
			dashMeta.setRegionType("state");
			log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY U.S. TOTALS");
			log.debug("Getting U.S. data for populating By U.S. Totals row of dashboard...");
			
			List<UnitedStatesData> usaData = externalDataGetterStore.getDataFor(RegionsInDashboard.USA,
					getExternalDataServiceFromFactory(RegionsInDashboard.USA.name()));

			dashStats = dashStatsStore.produceDashboardStatsForType(DashStatsTypes.DASHSTATS_BY_US_TOTALS, dashStats, usaData, null, regionPopulation);
		}
		
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_PER_CAPITA_STATS, dashStats, null, null, regionPopulation);
		
		//////// SET ALL DASHBOARD META DATA AND HEADER DATA ////////
		dashMeta.setPerCapitaBasis(ChartListConstants.PER_CAPITA_BASIS.getValue());
		dashHeader.setFullRegion(fullRegionString);
		if(fullRegionString.length() > MAX_REGION_LENGTH_TO_DISPLAY) {
			dashHeader.setFullRegion(fullRegionString.substring(0, MAX_REGION_LENGTH_TO_DISPLAY + 1) + "...");
		}
		dashHeader.setPopulation(regionPopulation);

		//////// WRITE META DATA, HEADER DATA, AND STATS TO ModelMap ////////
		map.addAttribute("dashmeta", dashMeta);
		map.addAttribute("dashheader", dashHeader);
		map.addAttribute("dashstats", dashStats);
		
		return true;
	}

	private IExternalDataConnectionService getExternalDataServiceFromFactory(String region) {
		try {
			IExternalDataConnectionService dataService = dataFactory.getExternalDataService(region);
			log.debug("Success, got the dataService: " + dataService.toString());
			return dataService;
		} catch (IllegalArgumentException e) {
			log.error("Unable to find data source for region '" + region + "'. No dashboard to build!");
			throw(e);
		}
	}	
}
