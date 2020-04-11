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
import com.royware.corona.dashboard.model.ChartConfig;
import com.royware.corona.dashboard.services.ChartListDataServiceImpl;

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
	RestTemplate restTemplate;
	
	@Autowired
	ChartListDataService dataService;
	
	@Bean
	public ChartListDataService dataService() {
		return new ChartListDataServiceImpl(restTemplate);
	}
	
	@Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("dataCache")));
        return cacheManager;
    }
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
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
			map.addAttribute("allDashboardData", makeChartListsForRendering(dataService.getAllUsData("COVID_TRACKING")));
			break;
		case REGION_US_NO_NY:
			map.addAttribute("allDashboardData", makeChartListsForRendering(dataService.getAllUsDataExcludingState(REGION_NY)));
			break;
		case REGION_NY:
			map.addAttribute("allDashboardData", makeChartListsForRendering(dataService.getSingleUsStateData(REGION_NY)));
			break;
		case REGION_AZ:
			map.addAttribute("allDashboardData", makeChartListsForRendering(dataService.getSingleUsStateData(REGION_AZ)));
			break;
		case REGION_ITA:
			map.addAttribute("allDashboardData", makeChartListsForRendering(dataService.getSingleNonUsCountryData(REGION_ITA)));
			break;
		}
		
		configureDashboardCharts(map, region);
		
		return DASHBOARD_PAGE;
	}
	
	private void configureDashboardCharts(ModelMap map, String region) {
		ChartConfig timeHistoryOfCasesChartConfig = new ChartConfig("Time History of Total Cases in " + region,
				"Days Since Cases > 0", "Total Cases", "scatter");
		timeHistoryOfCasesChartConfig.setxAxisPosition("bottom");
		timeHistoryOfCasesChartConfig.setxAxisLogarithmic("false");
		timeHistoryOfCasesChartConfig.setyAxisPosition("left");
		timeHistoryOfCasesChartConfig.setyAxisLogarithmic("false");
		timeHistoryOfCasesChartConfig.setShowLegend("true");
		timeHistoryOfCasesChartConfig.setDataPointSize(1);
		
		ChartConfig rateOfChangeOfCasesChartConfig = new ChartConfig("Rate of Change of Cases in " + region,
				"Days Since Cases > 0", "Percent Change in New Cases", "scatter");
		rateOfChangeOfCasesChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfCasesChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setyAxisPosition("left");
		rateOfChangeOfCasesChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setShowLegend("true");
		rateOfChangeOfCasesChartConfig.setDataPointSize(1);
		
		ChartConfig accelerationOfCasesChartConfig = new ChartConfig("Acceleration of Cases in " + region,
				"Days Since Cases > 0", "Percent Change in the Rate of New Cases", "scatter");
		accelerationOfCasesChartConfig.setxAxisPosition("bottom");
		accelerationOfCasesChartConfig.setxAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setyAxisPosition("left");
		accelerationOfCasesChartConfig.setyAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setShowLegend("true");
		accelerationOfCasesChartConfig.setDataPointSize(1);
		
		ChartConfig rateOfCasesVersusCasesChartConfig = new ChartConfig("Detecting Inflection in Cases in " + region,
				"Total Cases", "Daily Change in Total Cases", "scatter");
		rateOfCasesVersusCasesChartConfig.setxAxisPosition("bottom");
		rateOfCasesVersusCasesChartConfig.setxAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setyAxisPosition("left");
		rateOfCasesVersusCasesChartConfig.setyAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setShowLegend("true");
		rateOfCasesVersusCasesChartConfig.setDataPointSize(1);
		
		ChartConfig rateOfDeathsVersusDeathsChartConfig = new ChartConfig("Detecting Inflection in Deaths in " + region,
				"Total Deaths", "Daily Change in Total Deaths", "scatter");
		rateOfDeathsVersusDeathsChartConfig.setxAxisPosition("bottom");
		rateOfDeathsVersusDeathsChartConfig.setxAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setyAxisPosition("left");
		rateOfDeathsVersusDeathsChartConfig.setyAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setShowLegend("true");
		rateOfDeathsVersusDeathsChartConfig.setDataPointSize(1);
		
		ChartConfig rateOfChangeOfDeathsChartConfig = new ChartConfig("Rate of Change of Deaths in " + region,
				"Days Since Cases > 0", "Percent Change in New Deaths", "scatter");
		rateOfChangeOfDeathsChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfDeathsChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setyAxisPosition("left");
		rateOfChangeOfDeathsChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setShowLegend("true");
		rateOfChangeOfDeathsChartConfig.setDataPointSize(1);
		
		List<ChartConfig> chartConfigList = new ArrayList<>();
		chartConfigList.add(timeHistoryOfCasesChartConfig);
		chartConfigList.add(rateOfChangeOfCasesChartConfig);
		chartConfigList.add(accelerationOfCasesChartConfig);
		chartConfigList.add(rateOfCasesVersusCasesChartConfig);
		chartConfigList.add(rateOfDeathsVersusDeathsChartConfig);
		chartConfigList.add(rateOfChangeOfDeathsChartConfig);
		
		map.addAttribute("configList", chartConfigList);
	}

	private <T extends CanonicalCases> List<List<List<Map<Object, Object>>>> makeChartListsForRendering(List<T> caseList) {
		List<List<List<Map<Object, Object>>>> dashboardDataSetsList = new ArrayList<>();
		dashboardDataSetsList.add(chartService.getTotalCasesVersusTimeWithExponentialFit(caseList));
		dashboardDataSetsList.add(chartService.getDailyRateOfChangeOfCasesWithMovingAverage(caseList));
		dashboardDataSetsList.add(chartService.getDailyAccelerationOfCasesWithMovingAverage(caseList));
		dashboardDataSetsList.add(chartService.getChangeInTotalCasesVersusCaseswithExponentialLine(caseList));
		dashboardDataSetsList.add(chartService.getTotalCasesVersusTimeWithExponentialFit(caseList));
		dashboardDataSetsList.add(chartService.getTotalCasesVersusTimeWithExponentialFit(caseList));
		
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
