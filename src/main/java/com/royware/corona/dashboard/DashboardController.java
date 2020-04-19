//MAKE SURE THE POM IS NOT IN TEST MODE (SEE POM FOR DETAILS)
package com.royware.corona.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.royware.corona.dashboard.enums.Pages;
import com.royware.corona.dashboard.enums.Regions;
import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.DashboardConfigService;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.DashboardStatistics;

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
	private DashboardStatistics dashStats;
	
	@Autowired
	private DashboardConfigService dashConfigSvc;
	
	@Autowired
	@Qualifier(value = "us")
	private ExternalDataService usDataService;
	
	@Autowired
	@Qualifier(value = "world")
	private ExternalDataService worldDataService;
	
	@Autowired
	@Qualifier(value = "singleState")
	private ExternalDataService singleStateDataService;
	
	@Autowired
	@Qualifier(value = "usExcludingState")
	private ExternalDataService usExcludingStateDataService;
	
	@Autowired
	@Qualifier(value = "singleCountry")
	private ExternalDataService singleCountryDataService;
	
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
		
		List<? extends CanonicalCases> dataList = new ArrayList<>();
		ExternalDataService dataService;
		
		if(region.equalsIgnoreCase(Regions.USA.name())) {
			dataService = usDataService;
		} else if(region.equalsIgnoreCase(Regions.USA_NO_NY.name())) {
			dataService = usExcludingStateDataService;
		} else if(region.length() == 2) {
			dataService = singleStateDataService;
		} else {
			dataService = singleCountryDataService;
		}
		
		dataList = Regions.valueOf(region).getCoronaVirusDataFromExternalSource(dataService);
		map.addAttribute("allDashboardCharts", dashConfigSvc.makeAllDashboardCharts(dataList, Regions.valueOf(region).getRegionData().getFullName(), dashStats));
		
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
