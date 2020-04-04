//MAKE SURE THE POM IS NOT IN TEST MODE (SEE POM FOR DETAILS)
package com.royware.corona.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.interfaces.CanvasjsChartService;
import com.royware.corona.model.UnitedStatesData;
import com.royware.corona.services.DashboardService;

/**
 * The MAIN CONTROLLER for the dashboard application
 * Controls all HTTP GET and POST requests
 * All return statements are the names of Java Server Page (jsp) files
 */
@Controller
public class DashboardController {
	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	CanvasjsChartService canvasjsChartService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(/*RestTemplateBuilder builder*/) {
//		return builder.build();
		return new RestTemplate();
	}

	private static final String HOME_PAGE = "home-page";
	private static final String ABOUT_PAGE = "about-dashboard";
	private static final String MATH_PAGE = "math";
	private static final String COMMENTARY_PAGE = "commentary";
	private static final String DASHBOARD_PAGE = "dashboard";
	private static final String CHART_INFO_PAGE = "chart-info";
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	/**
	 * HTTP GET request handler for /corona to direct to the home page jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@RequestMapping(value = "/corona", method = RequestMethod.GET)
	public String showHomePage(@ModelAttribute("region") String region, ModelMap map) {
		map.addAttribute("region", region);
		return HOME_PAGE;
	}
	
	/**
	 * HTTP POST handler for a region dashboard
	 * @param region the selected geographic region for the dashboard
	 * @param map the ModelMap
	 * @return the jsp file name (dashboard)
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	public String makeRegionDashboard(@ModelAttribute("region") String region, ModelMap map) {		
		//ALL METHODS FOR MAKING DASHBOARDS CALLED FROM HERE
		log.info("STUB-OUT: Making dashboard for region: " + region);
		
		//Make chart data sets
		
		//US Data
		UnitedStatesData[] usData = restTemplate.getForObject("https://covidtracking.com/api/us/daily", UnitedStatesData[].class);
		log.info("The US data object array is:");
		for(UnitedStatesData usd : usData) {
			log.info(usd.toString());
		}
		
		//TODO: Write methods for making chart data for each type of chart
		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
		
		//Store chart data sets in a list to pass to the JSP
		List<List<List<Map<Object, Object>>>> dashboardDataSetsList = new ArrayList<>();
		dashboardDataSetsList.add(canvasjsDataList);
		dashboardDataSetsList.add(canvasjsDataList);
		dashboardDataSetsList.add(canvasjsDataList);
		dashboardDataSetsList.add(canvasjsDataList);
		dashboardDataSetsList.add(canvasjsDataList);
		dashboardDataSetsList.add(canvasjsDataList);

		//Update the model map with all data sets for rendering on the JSP page
		map.addAttribute("dataSets", dashboardDataSetsList);
		
		return DASHBOARD_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /about to direct to the about-dashboard jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String showAboutPage(ModelMap map) {		
		return ABOUT_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /commentary to direct to the commentary jsp
	 * @param map the current ModelMap
	 * @return a string for the commentary jsp
	 */
	@RequestMapping(value = "/commentary", method = RequestMethod.GET)
	public String showCommentaryPage(ModelMap map) {		
		return COMMENTARY_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /math to direct to the math jsp
	 * @param map the current ModelMap
	 * @return a string for the math jsp
	 */
	@RequestMapping(value = "/math", method = RequestMethod.GET)
	public String showMathPage(ModelMap map) {		
		return MATH_PAGE;
	}
	
	/**
	 * HTTP GET request handler for /chart-info to direct to the chart-info jsp
	 * @param map the current ModelMap
	 * @return a string for the chart-info jsp
	 */
	@RequestMapping(value = "/chart-info", method = RequestMethod.GET)
	public String showChartInfoPage(ModelMap map) {		
		return CHART_INFO_PAGE;
	}
}
