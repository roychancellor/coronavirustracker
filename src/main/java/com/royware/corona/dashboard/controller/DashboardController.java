//MAKE SURE THE POM IS NOT IN TEST MODE (SEE POM FOR DETAILS)
package com.royware.corona.dashboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.royware.corona.dashboard.enums.jsp.JspPageNames;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardConfigService;

/**
 * The MAIN CONTROLLER for the dashboard application
 * Controls all HTTP GET and POST requests
 * All return statements are the names of Java Server Page (jsp) files
 */
@EnableWebMvc
@Controller
public class DashboardController {
	@Autowired
	private DashboardConfigService dashboardConfigService;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	/**
	 * HTTP GET request handler for /corona to direct to the home page jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/corona")
	public String showHomePage(@ModelAttribute("region") String region, ModelMap map) {
		map.addAttribute("region", region);

		for(RegionsData regionEnum : RegionsData.values()) {
			map.addAttribute(regionEnum.name(), regionEnum.name());
		}

		return JspPageNames.HOME_PAGE.toString();
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

		if(!dashboardConfigService.populateDashboardModelMap(region, map)) {
			return "connect-error-popup";
		}
		return JspPageNames.DASHBOARD_PAGE.toString();
	}

	/**
	 * HTTP GET request handler for /about to direct to the about-dashboard jsp
	 * @param map the current ModelMap
	 * @return a string for the home page jsp
	 */
	@GetMapping(value = "/about")
	public String showAboutPage(ModelMap map) {		
		return JspPageNames.ABOUT_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /commentary to direct to the commentary jsp
	 * @param map the current ModelMap
	 * @return a string for the commentary jsp
	 */
	@GetMapping(value = "/commentary")
	public String showCommentaryPage(ModelMap map) {		
		return JspPageNames.COMMENTARY_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /math to direct to the math jsp
	 * @param map the current ModelMap
	 * @return a string for the math jsp
	 */
	@GetMapping(value = "/math")
	public String showMathPage(ModelMap map) {		
		return JspPageNames.MATH_PAGE.toString();
	}
	
	/**
	 * HTTP GET request handler for /chart-info to direct to the chart-info jsp
	 * @param map the current ModelMap
	 * @return a string for the chart-info jsp
	 */
	@GetMapping(value = "/chart-info")
	public String showChartInfoPage(ModelMap map) {		
		return JspPageNames.CHART_INFO_PAGE.toString();
	}
}
