package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.royware.corona.dashboard.enums.regions.Regions;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardConfigService;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardMultiRegionService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Component
public class DashboardConfigServiceImpl implements DashboardConfigService {
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	@Autowired
	private DashboardStatistics dashStats;
	
	@Autowired
	private DashboardChartService dashboardChartService;
	
	@Autowired
	private DashboardMultiRegionService dashboardMultiRegionService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardConfigServiceImpl.class);
	
	@Override
	public boolean populateDashboardModelMap(String rawRegionString, ModelMap map) {
		List<? extends CanonicalData> dataList = new ArrayList<>();
		String fullRegionString;
		int regionPopulation;
		boolean isMultiRegion = rawRegionString.length() > 3 ? rawRegionString.substring(0,5).equalsIgnoreCase("MULTI") : false;
		final int MAX_REGION_LENGTH_TO_DISPLAY = 28;
		
		ExternalDataService dataService = getExternalDataServiceFromFactory(rawRegionString);
		if(dataService == null) {
			return false;
		}
		
		//Check for empty multi-region
		if(isMultiRegion && rawRegionString.substring(6).length() < 2) {
			return false;
		}
		
		//Need to get the data differently for a multi-region selection 
		if(isMultiRegion) {
			fullRegionString = rawRegionString;
			String regionsOnlyCsvString = dashboardMultiRegionService.getStatesFromMultiRegionString(rawRegionString);
			regionPopulation = dashboardMultiRegionService.getMultiRegionPopulation(regionsOnlyCsvString);
			dataList = dashboardMultiRegionService.getMultiRegionDataFromExternalSource(regionsOnlyCsvString, dataService);
		} else {
			fullRegionString = Regions.valueOf(rawRegionString).getRegionData().getFullName();
			regionPopulation = Regions.valueOf(rawRegionString).getRegionData().getPopulation();
			dataList = Regions.valueOf(rawRegionString).getCoronaVirusDataFromExternalSource(dataService);
		}
		log.info("Finished making the data list...");
		
		//Check for a null or empty data list. This is VERY important!!!
		if(dataList == null || dataList.isEmpty()) {
			return false;
		}
		
		log.info("About to call makeAllDashboardCharts with region = " + fullRegionString);
		map.addAttribute("allDashboardCharts", dashboardChartService.makeAllDashboardCharts(dataList, fullRegionString, regionPopulation, dashStats));
		log.info("Done calling makeAllDashboardCharts");
		
		//This setting determines whether the last row of the statistics table will show
		map.addAttribute("regionType", "us");
		if(rawRegionString.length() == 3 && !rawRegionString.equalsIgnoreCase("USA")) {
			map.put("regionType", "world");
		} else if(rawRegionString.length() == 2 || isMultiRegion) {
			map.put("regionType", "state");
			dashboardChartService.makeDashboardRowByUsTotals(regionPopulation, dashStats);
		}

		map.addAttribute("fullregion", fullRegionString);
		if(fullRegionString.length() > MAX_REGION_LENGTH_TO_DISPLAY) {
			map.addAttribute("fullregion", fullRegionString.substring(0, MAX_REGION_LENGTH_TO_DISPLAY + 1) + "...");
		}
		map.addAttribute("population", regionPopulation);
		map.addAttribute("casespermillion", dashStats.getCasesTotal() * 1000000.0 / regionPopulation);
		map.addAttribute("casespercent", dashStats.getCasesTotal() * 100.0 / regionPopulation);
		map.addAttribute("deathspermillion", dashStats.getDeathsTotal() * 1000000.0 / regionPopulation);
		map.addAttribute("deathspercent", dashStats.getDeathsTotal() * 100.0 / regionPopulation);
		map.addAttribute("dashstats", dashStats);
		
		return true;
	}

	private ExternalDataService getExternalDataServiceFromFactory(String region) {
		try {
			ExternalDataService dataService = dataFactory.getExternalDataService(region);
			log.info("Success, got the dataService: " + dataService.toString());
			return dataService;
		} catch (IllegalArgumentException e) {
			log.error("Unable to find data source for region '" + region + "'. No dashboard to build!");
			throw(e);
		}
	}	
}
