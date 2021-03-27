package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardConfigService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.data.IMultiRegionExternalDataService;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardHeader;
import com.royware.corona.dashboard.model.dashboard.DashboardMeta;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Component
public class DashboardConfigServiceImpl implements DashboardConfigService {
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	@Autowired
	private DashboardStatistics dashStats;
	
	@Autowired
	private DashboardMeta dashMeta;
	
	@Autowired
	private DashboardHeader dashHeader;
	
	@Autowired
	private DashboardChartService dashboardChartService;
	
	@Autowired
	private IMultiRegionExternalDataService dashboardMultiRegionService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardConfigServiceImpl.class);
	
	@Override
	public boolean populateDashboardModelMap(String rawRegionString, ModelMap map) {
		List<? extends CanonicalCaseDeathData> dataList = new ArrayList<>();
		String fullRegionString;
		int regionPopulation;
		boolean isMultiRegion = rawRegionString.length() > 3 ? rawRegionString.substring(0,5).equalsIgnoreCase("MULTI") : false;
		final int MAX_REGION_LENGTH_TO_DISPLAY = 28;
		
		//Check for null data service or an empty multi-region
		ExternalDataService dataService = getExternalDataServiceFromFactory(rawRegionString);
		if(dataService == null || (isMultiRegion && rawRegionString.substring(6).length() < 2)) {
			log.error("In populateDashboardModelMap: Unable to getExternalDataServiceFromFactory");
			return false;
		}
		
		//Need to get the data differently for a multi-region selection 
		//As of 03/07/2021, there is no longer a single source of data available for the U.S., so need to treat it like a multi-region
		if(isMultiRegion) {
			fullRegionString = rawRegionString;
			String regionsOnlyCsvString = dashboardMultiRegionService.getStatesFromMultiRegionString(rawRegionString);
			regionPopulation = dashboardMultiRegionService.getMultiRegionPopulation(regionsOnlyCsvString);
			dataList = dashboardMultiRegionService.getMultiRegionDataFromExternalSource(regionsOnlyCsvString, dataService);
		} else {
			fullRegionString = RegionsData.valueOf(rawRegionString).getRegionData().getFullName();
			regionPopulation = RegionsData.valueOf(rawRegionString).getRegionData().getPopulation();
			dataList = RegionsData.valueOf(rawRegionString).getCoronaVirusDataFromExternalSource(dataService);
		}
		log.debug("fullRegionString: " + fullRegionString + ", regionPopulation: " + regionPopulation + ", dataList size: " + dataList.size());
		log.info("Finished making the data list...");
		
		//Check for a null or empty data list. This is VERY important!!!
		if(dataList == null || dataList.isEmpty()) {
			log.error("In populateDashboardModelMap: The dataList is null or empty");
			return false;
		}
		
		log.info("About to call makeAllDashboardCharts with region = " + fullRegionString);
		map.addAttribute("allDashboardCharts", dashboardChartService.makeAllDashboardCharts(dataList, fullRegionString, regionPopulation, dashStats));
		log.info("Done calling makeAllDashboardCharts");
		
		//This setting determines whether the last row of the statistics table will show
		boolean populateUSTotals = true;
		dashMeta.setRegionType("us");
		if(rawRegionString.length() == 3 && !rawRegionString.equalsIgnoreCase("USA")) {
			dashMeta.setRegionType("world");
		} else if((rawRegionString.length() == 2 || isMultiRegion) && populateUSTotals) {
			dashMeta.setRegionType("state");
			dashboardChartService.makeDashboardRowByUsTotals(regionPopulation, dashStats);
		}
		dashMeta.setPerCapitaBasis(MovingAverageSizes.PER_CAPITA_BASIS.getValue());

		dashHeader.setFullRegion(fullRegionString);
		if(fullRegionString.length() > MAX_REGION_LENGTH_TO_DISPLAY) {
			dashHeader.setFullRegion(fullRegionString.substring(0, MAX_REGION_LENGTH_TO_DISPLAY + 1) + "...");
		}
		dashHeader.setPopulation(regionPopulation);
		
		dashStats.setCasesPerCapita(dashStats.getCasesTotal() * 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue() / regionPopulation);
		dashStats.setCasesPercentOfPop(dashStats.getCasesTotal() * 100.0 / regionPopulation);
		dashStats.setDeathsPerCapita(dashStats.getDeathsTotal() * 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue() / regionPopulation);
		dashStats.setDeathsPercentOfPop(dashStats.getDeathsTotal() * 100.0 / regionPopulation);
		
		map.addAttribute("dashmeta", dashMeta);
		map.addAttribute("dashheader", dashHeader);
		map.addAttribute("dashstats", dashStats);
		
		return true;
	}

	private ExternalDataService getExternalDataServiceFromFactory(String region) {
		try {
			ExternalDataService dataService = dataFactory.getExternalDataService(region);
			log.debug("Success, got the dataService: " + dataService.toString());
			return dataService;
		} catch (IllegalArgumentException e) {
			log.error("Unable to find data source for region '" + region + "'. No dashboard to build!");
			throw(e);
		}
	}	
}
