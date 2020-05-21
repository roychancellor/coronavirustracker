package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.royware.corona.dashboard.DashboardController;
import com.royware.corona.dashboard.enums.Regions;
import com.royware.corona.dashboard.interfaces.CanonicalData;
import com.royware.corona.dashboard.interfaces.ChartService;
import com.royware.corona.dashboard.interfaces.DashboardChartService;
import com.royware.corona.dashboard.interfaces.DashboardConfigService;
import com.royware.corona.dashboard.interfaces.DashboardMultiRegionService;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.interfaces.ExternalDataServiceFactory;
import com.royware.corona.dashboard.model.DashboardStatistics;
import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public class DashboardConfigServiceImpl implements DashboardConfigService {
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	@Autowired
	private DashboardStatistics dashStats;
	
	@Autowired
	private DashboardChartService dashboardChartService;
	
	@Autowired
	private DashboardMultiRegionService dashboardMultiRegionService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
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
		
		log.info("About to call makeAllDashboardCharts with region = " + fullRegionString);
		map.addAttribute("allDashboardCharts", dashboardChartService.makeAllDashboardCharts(dataList, fullRegionString, dashStats));
		log.info("Done calling makeAllDashboardCharts");
		
		//This setting determines whether the last row of the statistics table will show
		map.addAttribute("regionType", "us");
		if(rawRegionString.length() == 3 && !rawRegionString.equalsIgnoreCase("USA")) {
			map.put("regionType", "world");
		} else if(rawRegionString.length() == 2 || isMultiRegion) {
			log.info("Getting U.S. data for populating By U.S. Totals row of dashboard...");
			makeByUsTotalsRowOfDashboard(map, regionPopulation);
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
	
	private void makeByUsTotalsRowOfDashboard(ModelMap map, int regionPopulation) {
		map.put("regionType", "state");
		List<UnitedStatesData> usaData = Regions.USA.getCoronaVirusDataFromExternalSource(dataFactory.getExternalDataService(Regions.USA.name()));
		int totalUsCases = usaData.get(usaData.size() - 1).getTotalPositiveCases();
		map.addAttribute("totaluscases", totalUsCases);
		map.addAttribute("casesregion_totaluscases", dashStats.getCasesTotal() * 100.0 / totalUsCases);
		int totalUsDeaths = usaData.get(usaData.size() - 1).getTotalDeaths();
		map.addAttribute("totalusdeaths", totalUsDeaths);
		map.addAttribute("deathsregion_totalusdeaths", dashStats.getDeathsTotal() * 100.0 / totalUsDeaths);
		map.addAttribute("regionpop_uspop", regionPopulation * 100.0 / Regions.USA.getRegionData().getPopulation());
	}	
}
