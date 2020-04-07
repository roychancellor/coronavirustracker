//MAKE SURE THE POM IS NOT IN TEST MODE (SEE POM FOR DETAILS)
package com.royware.corona.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.interfaces.ChartService;
import com.royware.corona.dashboard.model.DataListBean;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldCases;

/**
 * The MAIN CONTROLLER for the dashboard application
 * Controls all HTTP GET and POST requests
 * All return statements are the names of Java Server Page (jsp) files
 */
@Controller
@ComponentScan("com.royware.corona")
@Configuration
@EnableWebMvc
public class DashboardController {
	@Autowired
	ChartService chartService;
	
	@Autowired
	ChartListDataService dataService;
	
	@Autowired
	DataListBean dataListsBean;
	
	private static final String HOME_PAGE = "home-page";
	private static final String ABOUT_PAGE = "about-dashboard";
	private static final String MATH_PAGE = "math";
	private static final String COMMENTARY_PAGE = "commentary";
	private static final String DASHBOARD_PAGE = "dashboard";
	private static final String CHART_INFO_PAGE = "chart-info";
	private static final String REGION_US = "us";
	private static final String REGION_US_NO_NY = "us_no_ny";
	private static final String REGION_NY = "ny";
	private static final String REGION_AZ = "az";
	private static final String REGION_ITA = "ita";
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private List<UnitedStatesCases> usCaseList;
	private List<WorldCases> worldCaseList;
	
	/**
	 * HTTP GET request handler for /corona to direct to the home page jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/corona")
	public String showHomePage(@ModelAttribute("region") String region, ModelMap map) {
		map.addAttribute("region", region);
		map.addAttribute("us", REGION_US);
		map.addAttribute("us_no_ny", REGION_US_NO_NY);
		map.addAttribute("ny", REGION_NY);
		map.addAttribute("az", REGION_AZ);
		map.addAttribute("ita", REGION_ITA);
		return HOME_PAGE;
	}
	
	/**
	 * HTTP POST handler for making a region dashboard
	 * @param region the selected geographic region for the dashboard
	 * @param map the ModelMap
	 * @return the jsp file name (dashboard)
	 */
	@PostMapping(value = "/dashboard")
	public String makeRegionDashboard(@ModelAttribute("region") String region, ModelMap map) {		
		log.info("STUB-OUT: Making dashboard for region: " + region);		
		
		switch(region) {
		case REGION_US:
			usCaseList = dataService.getAllUsData();
			map.addAttribute("allDashboardData", makeChartListsForRendering(usCaseList));
			break;
		case REGION_US_NO_NY:
			usCaseList = dataService.getAllUsDataExcludingState(REGION_NY);
			map.addAttribute("allDashboardData", makeChartListsForRendering(usCaseList));
			break;
		case REGION_NY:
			usCaseList = dataService.getSingleUsStateData(REGION_NY);
			map.addAttribute("allDashboardData", makeChartListsForRendering(usCaseList));
			break;
		case REGION_AZ:
			usCaseList = dataService.getSingleUsStateData(REGION_AZ);
			map.addAttribute("allDashboardData", makeChartListsForRendering(usCaseList));
			break;
		case REGION_ITA:
			worldCaseList = dataService.getSingleNonUsCountryData(REGION_ITA);
			map.addAttribute("allDashboardData", makeChartListsForRendering(worldCaseList));
			break;
		}
		
		return DASHBOARD_PAGE;
	}

	private <T extends CanonicalCases> List<List<List<Map<Object, Object>>>> makeChartListsForRendering(List<T> caseList) {
		List<List<List<Map<Object, Object>>>> dashboardDataSetsList = new ArrayList<>();
		List<List<Map<Object, Object>>> chartDataList;
		chartDataList = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		dashboardDataSetsList.add(chartDataList);
		chartDataList.clear();
		chartDataList = chartService.getDailyRateOfChangeOfCasesWithMovingAverage(caseList);
		dashboardDataSetsList.add(chartDataList);
		chartDataList.clear();
		chartDataList = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		dashboardDataSetsList.add(chartDataList);
		chartDataList.clear();
		chartDataList = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		dashboardDataSetsList.add(chartDataList);
		chartDataList.clear();
		chartDataList = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		dashboardDataSetsList.add(chartDataList);
		chartDataList.clear();
		chartDataList = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		dashboardDataSetsList.add(chartDataList);
		
		return dashboardDataSetsList;
	}
	
	/**
	 * HTTP GET request handler for /about to direct to the about-dashboard jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/about")
	public String showAboutPage(ModelMap map) {		
		return ABOUT_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /commentary to direct to the commentary jsp
	 * @param map the current ModelMap
	 * @return a string for the commentary jsp
	 */
	@GetMapping(value = "/commentary")
	public String showCommentaryPage(ModelMap map) {		
		return COMMENTARY_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /math to direct to the math jsp
	 * @param map the current ModelMap
	 * @return a string for the math jsp
	 */
	@GetMapping(value = "/math")
	public String showMathPage(ModelMap map) {		
		return MATH_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /chart-info to direct to the chart-info jsp
	 * @param map the current ModelMap
	 * @return a string for the chart-info jsp
	 */
	@GetMapping(value = "/chart-info")
	public String showChartInfoPage(ModelMap map) {		
		return CHART_INFO_PAGE;
	}
}
