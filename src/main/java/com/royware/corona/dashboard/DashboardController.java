//MAKE SURE THE POM IS NOT IN TEST MODE (SEE POM FOR DETAILS)
package com.royware.corona.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.interfaces.ChartService;
import com.royware.corona.dashboard.model.DashboardChartConfig;
import com.royware.corona.dashboard.model.DashboardChartData;
import com.royware.corona.dashboard.model.DashboardStatistics;
import com.royware.corona.dashboard.model.Pages;
import com.royware.corona.dashboard.model.Regions;
import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.services.ChartListDataServiceImpl;
import com.royware.corona.dashboard.services.DashboardConfigService;

/**
 * The MAIN CONTROLLER for the dashboard application
 * Controls all HTTP GET and POST requests
 * All return statements are the names of Java Server Page (jsp) files
 */
@Configuration
@EnableCaching
@EnableWebMvc
@Controller
@ComponentScan("com.royware.corona")
public class DashboardController {
	@Autowired
	ChartService chartService;
	
	@Autowired
	ChartListDataService dataService;
	
	@Autowired
	DashboardStatistics dashStats;
	
	@Autowired
	DashboardConfigService dashConfigSvc;
	
	private static final String CACHE_KEY = "COVID_TRACKING";
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	/**
	 * HTTP GET request handler for /corona to direct to the home page jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/corona")
	public String showHomePage(@ModelAttribute("region") String region, ModelMap map) {
		map.addAttribute("region", region);

		for(Regions regionEnum : Regions.values()) {
			map.addAttribute(regionEnum.name(), regionEnum.name());
		}

		return Pages.HOME_PAGE.toString();
	}
	
	/**
	 * HTTP POST handler for making a region dashboard
	 * @param region the selected geographic region for the dashboard
	 * @param map the ModelMap
	 * @return the jsp file name (dashboard)
	 */
	@PostMapping(value = "/dashboard")
	public String makeRegionDashboard(@ModelAttribute("region") String region, ModelMap map) {		
		log.info("Making dashboard for region: " + region);	
		
		List<UnitedStatesCases> usList = new ArrayList<>();
		List<WorldCases> worldList = new ArrayList<>();
		
		if(Regions.valueOf(region.toUpperCase()) == Regions.USA) {
			usList = Regions.USA.getRegionDataList(dataService, CACHE_KEY);
			map.addAttribute("allDashboardCharts", dashConfigSvc.makeAllDashboardCharts(usList, Regions.USA.getRegionData().getFullName(), dashStats));
		} else if(Regions.valueOf(region.toUpperCase()) == Regions.USA_NO_NY) {
			usList = Regions.USA_NO_NY.getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", dashConfigSvc.makeAllDashboardCharts(usList, Regions.USA_NO_NY.getRegionData().getFullName(), dashStats));
		} else if(region.length() == 2) {
			usList = Regions.valueOf(region).getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", dashConfigSvc.makeAllDashboardCharts(usList, Regions.valueOf(region).getRegionData().getFullName(), dashStats));
		} else {
			worldList = Regions.valueOf(region).getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", dashConfigSvc.makeAllDashboardCharts(worldList, Regions.valueOf(region).getRegionData().getFullName(), dashStats));
		}
		
		map.addAttribute("fullregion", Regions.valueOf(region).getRegionData().getFullName());
		map.addAttribute("population", Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("casespermillion", dashStats.getCasesTotal() * 1000000.0 / Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("casespercent", dashStats.getCasesTotal() * 100.0 / Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("deathspermillion", dashStats.getDeathsTotal() * 1000000.0 / Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("deathspercent", dashStats.getDeathsTotal() * 100.0 / Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("dashstats", dashStats);

		return Pages.DASHBOARD_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /about to direct to the about-dashboard jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/about")
	public String showAboutPage(ModelMap map) {		
		return Pages.ABOUT_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /commentary to direct to the commentary jsp
	 * @param map the current ModelMap
	 * @return a string for the commentary jsp
	 */
	@GetMapping(value = "/commentary")
	public String showCommentaryPage(ModelMap map) {		
		return Pages.COMMENTARY_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /math to direct to the math jsp
	 * @param map the current ModelMap
	 * @return a string for the math jsp
	 */
	@GetMapping(value = "/math")
	public String showMathPage(ModelMap map) {		
		return Pages.MATH_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /chart-info to direct to the chart-info jsp
	 * @param map the current ModelMap
	 * @return a string for the chart-info jsp
	 */
	@GetMapping(value = "/chart-info")
	public String showChartInfoPage(ModelMap map) {		
		return Pages.CHART_INFO_PAGE.toString();
	}
}
