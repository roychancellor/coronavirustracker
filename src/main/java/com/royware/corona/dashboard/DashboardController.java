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
import com.royware.corona.dashboard.model.Regions;
import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldCases;
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
	
	@Autowired
	DashboardStatistics dashStats;
	
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
		map.addAttribute(Regions.USA.name(), Regions.USA.name());
		map.addAttribute(Regions.USA_NO_NY.name(), Regions.USA_NO_NY.name());
		map.addAttribute(Regions.NY.name(), Regions.NY.name());
		map.addAttribute(Regions.AZ.name(), Regions.AZ.name());
		map.addAttribute(Regions.ITA.name(), Regions.ITA.name());
		map.addAttribute(Regions.DEU.name(), Regions.DEU.name());

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
		log.info("Making dashboard for region: " + region);	
		
		List<UnitedStatesCases> usList = new ArrayList<>();
		List<WorldCases> worldList = new ArrayList<>();
		
		if(Regions.valueOf(region.toUpperCase()) == Regions.USA) {
			usList = Regions.USA.getRegionDataList(dataService, CACHE_KEY);
			map.addAttribute("allDashboardCharts", makeAllDashboardCharts(usList, Regions.USA.getRegionData().getFullName(), dashStats));
		} else if(Regions.valueOf(region.toUpperCase()) == Regions.USA_NO_NY) {
			usList = Regions.USA_NO_NY.getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", makeAllDashboardCharts(usList, Regions.USA_NO_NY.getRegionData().getFullName(), dashStats));
		} else if(region.length() == 2) {
			usList = Regions.valueOf(region).getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", makeAllDashboardCharts(usList, Regions.valueOf(region).getRegionData().getFullName(), dashStats));
		} else {
			worldList = Regions.valueOf(region).getRegionDataList(dataService, region);
			map.addAttribute("allDashboardCharts", makeAllDashboardCharts(worldList, Regions.valueOf(region).getRegionData().getFullName(), dashStats));
		}
		
		map.addAttribute("fullregion", Regions.valueOf(region).getRegionData().getFullName());
		map.addAttribute("population", Regions.valueOf(region).getRegionData().getPopulation());
		map.addAttribute("dashstats", dashStats);

		return DASHBOARD_PAGE;
	}
	
	private <T extends CanonicalCases> List<Dashboard> makeAllDashboardCharts(List<T> caseList, String region, DashboardStatistics dashStats) {
		List<Dashboard> dashboardList = new ArrayList<>();
		List<List<Map<Object, Object>>> dataCasesByTime = chartService.getTotalCasesVersusTimeWithExponentialFit(caseList);
		List<List<Map<Object, Object>>> dataRateOfCasesByTime = chartService.getDailyRateOfChangeOfCasesWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataAccelOfCasesByTime = chartService.getDailyAccelerationOfCasesWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataChangeOfCasesByCases = chartService.getChangeInTotalCasesVersusCaseswithExponentialLine(caseList);
		List<List<Map<Object, Object>>> dataChangeOfDeathsByDeaths = chartService.getChangeInTotalDeathsVersusDeathsswithExponentialLine(caseList);
		List<List<Map<Object, Object>>> dataRateOfDeathsByTime = chartService.getDailyRateOfChangeOfDeathsWithMovingAverage(caseList);
		
		//Set all the individual dashboard statistics
		dashStats.setCasesTotal((int)dataCasesByTime.get(0).get(dataCasesByTime.size() - 1).get("y"));
		dashStats.setCasesToday((int)dataCasesByTime.get(0).get(dataCasesByTime.size() - 1).get("y")
				- (int)dataCasesByTime.get(0).get(dataCasesByTime.size() - 2).get("y"));
		dashStats.setRateOfCasesToday((double)dataRateOfCasesByTime.get(0).get(dataCasesByTime.size() - 1).get("y"));
		dashStats.setAccelOfCasesToday((double)dataAccelOfCasesByTime.get(0).get(dataCasesByTime.size() - 1).get("y"));
		dashStats.setDeathsTotal((int)dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.size() - 1).get("x"));
		dashStats.setDeathsToday((int)dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.size() - 1).get("x")
				- (int)dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.size() - 2).get("x"));
		dashStats.setRateOfDeathsToday((double)dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.size() - 1).get("y"));

		/////Configure all the dashboards individually
		DashboardChartConfig configCasesByTime = new DashboardChartConfig("Total Cases in " + region,
				"Days Since Cases > 0", "Total Cases", "scatter");
		configCasesByTime.setyAxisNumberSuffix("");
		configCasesByTime.setxAxisPosition("bottom");
		configCasesByTime.setxAxisLogarithmic("false");
		configCasesByTime.setyAxisPosition("left");
		configCasesByTime.setyAxisLogarithmic("false");
		configCasesByTime.setShowLegend("true");
		configCasesByTime.setDataPointSize(1);
		configCasesByTime.setxGridDashType("dot");
		configCasesByTime.setxAxisMin(0);
		int maxX = (int)dataCasesByTime.get(0).get(dataCasesByTime.get(0).size() - 1).get("x");
		configCasesByTime.setxAxisMax(maxX / 10 * 10 + (int)Math.pow(10, (int)Math.log10(maxX)));
		configCasesByTime.setyAxisMin(0);
		int maxY = getMaxValueFromListOfXYMaps(dataCasesByTime.get(0));
		int factor = (int)Math.pow(10, (int)Math.log10(maxY));
		configCasesByTime.setyAxisMax(maxY / factor * factor + factor);
		configCasesByTime.setyAxisInterval(factor);
//		log.info("1: factor = " + factor + ", maxY = " + maxY + ", yAxisMax = " + configCasesByTime.getyAxisMax());

		dashboardList.add(new Dashboard(new DashboardChartData(dataCasesByTime), configCasesByTime));
		
		DashboardChartConfig rateOfChangeOfCasesChartConfig = new DashboardChartConfig("Rate of Change of Cases in " + region,
				"Days Since Cases > 0", "Percent Change in New Cases", "scatter");
		rateOfChangeOfCasesChartConfig.setyAxisNumberSuffix("%");
		rateOfChangeOfCasesChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfCasesChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setyAxisPosition("left");
		rateOfChangeOfCasesChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setShowLegend("true");
		rateOfChangeOfCasesChartConfig.setDataPointSize(1);
		rateOfChangeOfCasesChartConfig.setxGridDashType("dot");
		rateOfChangeOfCasesChartConfig.setxAxisMin(0);
		maxX = (int)dataRateOfCasesByTime.get(0).get(dataRateOfCasesByTime.get(0).size() - 1).get("x");
		rateOfChangeOfCasesChartConfig.setxAxisMax(maxX / 10 * 10 + (int)Math.pow(10, (int)Math.log10(maxX)));
		rateOfChangeOfCasesChartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(dataRateOfCasesByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		factor = (int)Math.pow(10, (int)Math.log10(maxY));
		rateOfChangeOfCasesChartConfig.setyAxisMax(maxY / factor * factor + factor);
		rateOfChangeOfCasesChartConfig.setyAxisInterval(factor);

		dashboardList.add(new Dashboard(new DashboardChartData(dataRateOfCasesByTime), rateOfChangeOfCasesChartConfig));
		
		DashboardChartConfig accelerationOfCasesChartConfig = new DashboardChartConfig("Acceleration of Cases in " + region,
				"Days Since Cases > 0", "Percent Change in the Rate of New Cases", "scatter");
		accelerationOfCasesChartConfig.setyAxisNumberSuffix("%");
		accelerationOfCasesChartConfig.setxAxisPosition("bottom");
		accelerationOfCasesChartConfig.setxAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setyAxisPosition("left");
		accelerationOfCasesChartConfig.setyAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setShowLegend("true");
		accelerationOfCasesChartConfig.setDataPointSize(1);
		accelerationOfCasesChartConfig.setxGridDashType("dot");
		accelerationOfCasesChartConfig.setxAxisMin(0);
		maxX = (int)dataAccelOfCasesByTime.get(0).get(dataAccelOfCasesByTime.get(0).size() - 1).get("x");
		accelerationOfCasesChartConfig.setxAxisMax(maxX / 10 * 10 + (int)Math.pow(10, (int)Math.log10(maxX)));
		int minY = getMinValueFromListOfXYMaps(dataAccelOfCasesByTime.get(0));
		maxY = getMaxValueFromListOfXYMaps(dataAccelOfCasesByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		minY = minY < -100 ? -99 : minY;
		factor = (int)Math.pow(10, (int)Math.log10(minY));
		accelerationOfCasesChartConfig.setyAxisMin(minY / factor * factor - factor);
		factor = (int)Math.pow(10, (int)Math.log10(maxY));
		accelerationOfCasesChartConfig.setyAxisInterval(factor);
		accelerationOfCasesChartConfig.setyAxisMax(maxY / factor * factor + factor);
		
		dashboardList.add(new Dashboard(new DashboardChartData(dataAccelOfCasesByTime), accelerationOfCasesChartConfig));
		
		DashboardChartConfig rateOfCasesVersusCasesChartConfig = new DashboardChartConfig("Detecting Inflection of Cases in " + region,
				"Total Cases", "Daily Change in Total Cases", "scatter");
		rateOfCasesVersusCasesChartConfig.setyAxisNumberSuffix("");
		rateOfCasesVersusCasesChartConfig.setxAxisPosition("bottom");
		rateOfCasesVersusCasesChartConfig.setxAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setyAxisPosition("left");
		rateOfCasesVersusCasesChartConfig.setyAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setShowLegend("true");
		rateOfCasesVersusCasesChartConfig.setDataPointSize(1);
		rateOfCasesVersusCasesChartConfig.setxGridDashType("dot");
		rateOfCasesVersusCasesChartConfig.setxAxisMin((int)Math.pow(10, (int)Math.log10((Integer)dataChangeOfCasesByCases.get(0).get(0).get("x"))));
		rateOfCasesVersusCasesChartConfig.setxAxisMax((int)Math.pow(10,
					1 + (int)Math.log10((Integer)dataChangeOfCasesByCases.get(0).get(dataChangeOfCasesByCases.get(0).size() - 1).get("x"))));
		Double minValue = (Double)dataChangeOfCasesByCases.get(0).get(0).get("y");
		int digits = minValue > 0 ? (int)Math.log10((Double)dataChangeOfCasesByCases.get(0).get(0).get("y")) : 1;
		rateOfCasesVersusCasesChartConfig.setyAxisMin((int)Math.pow(10, digits));
		rateOfCasesVersusCasesChartConfig.setyAxisMax((int)Math.pow(10,
					1 + (int)Math.log10((double)getMaxValueFromListOfXYMaps(dataChangeOfCasesByCases.get(1)))));
		
		dashboardList.add(new Dashboard(new DashboardChartData(dataChangeOfCasesByCases), rateOfCasesVersusCasesChartConfig));
		
		DashboardChartConfig rateOfDeathsVersusDeathsChartConfig = new DashboardChartConfig("Detecting Inflection of Deaths in " + region,
				"Total Deaths", "Daily Change in Total Deaths", "scatter");
		rateOfDeathsVersusDeathsChartConfig.setyAxisNumberSuffix("");
		rateOfDeathsVersusDeathsChartConfig.setxAxisPosition("bottom");
		rateOfDeathsVersusDeathsChartConfig.setxAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setyAxisPosition("left");
		rateOfDeathsVersusDeathsChartConfig.setyAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setShowLegend("true");
		rateOfDeathsVersusDeathsChartConfig.setDataPointSize(1);
		rateOfDeathsVersusDeathsChartConfig.setxGridDashType("dot");
		rateOfDeathsVersusDeathsChartConfig.setxAxisMin((int)Math.pow(10, (int)Math.log10((Integer)dataChangeOfDeathsByDeaths.get(0).get(0).get("x"))));
		rateOfDeathsVersusDeathsChartConfig.setxAxisMax((int)Math.pow(10,
					1 + (int)Math.log10((Integer)dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.get(0).size() - 1).get("x"))));
		minValue = (Double)dataChangeOfDeathsByDeaths.get(0).get(0).get("y");
		digits = minValue > 0 ? (int)Math.log10((Double)dataChangeOfDeathsByDeaths.get(0).get(0).get("y")) : 1;
		rateOfDeathsVersusDeathsChartConfig.setyAxisMin((int)Math.pow(10, digits));
		rateOfDeathsVersusDeathsChartConfig.setyAxisMax((int)Math.pow(10,
					1 + (int)Math.log10((double)getMaxValueFromListOfXYMaps(dataChangeOfDeathsByDeaths.get(1)))));
		
		dashboardList.add(new Dashboard(new DashboardChartData(dataChangeOfDeathsByDeaths), rateOfDeathsVersusDeathsChartConfig));
		
		DashboardChartConfig rateOfChangeOfDeathsChartConfig = new DashboardChartConfig("Rate of Change of Deaths in " + region,
				"Days Since Cases > 0", "Percent Change in New Deaths", "scatter");
		rateOfChangeOfDeathsChartConfig.setyAxisNumberSuffix("%");
		rateOfChangeOfDeathsChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfDeathsChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setyAxisPosition("left");
		rateOfChangeOfDeathsChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setShowLegend("true");
		rateOfChangeOfDeathsChartConfig.setDataPointSize(1);
		rateOfChangeOfDeathsChartConfig.setxAxisGridlinesDisplay("dot");
		rateOfChangeOfDeathsChartConfig.setxAxisMin(0);
		maxX = (int)dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.get(0).size() - 1).get("x");
		rateOfChangeOfDeathsChartConfig.setxAxisMax(maxX / 10 * 10 + (int)Math.pow(10, (int)Math.log10(maxX)));
		rateOfChangeOfDeathsChartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(dataRateOfDeathsByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		factor = (int)Math.pow(10, (int)Math.log10(maxY));
		rateOfChangeOfDeathsChartConfig.setyAxisInterval(factor);
		rateOfChangeOfDeathsChartConfig.setyAxisMax(maxY / factor * factor + factor);
		
		dashboardList.add(new Dashboard(new DashboardChartData(dataRateOfDeathsByTime), rateOfChangeOfDeathsChartConfig));
		
		return dashboardList;
	}
	
	private int getMinValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		Double min = Double.valueOf(dataList.get(0).get("y").toString());
		
		for(Map<Object, Object> xy : dataList) {
			if(Double.valueOf(xy.get("y").toString()) < min) {
				min = Double.valueOf(xy.get("y").toString());
			}
		}
		return min.intValue();
	}
	
	private int getMaxValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		Double max = Double.valueOf(dataList.get(0).get("y").toString());
		
		for(Map<Object, Object> xy : dataList) {
			if(Double.valueOf(xy.get("y").toString()) > max) {
				max = Double.valueOf(xy.get("y").toString());
			}
		}
		return max.intValue();
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
