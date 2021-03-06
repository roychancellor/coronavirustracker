package com.royware.corona.dashboard.enums.jsp;

public enum JspPageNames {
	HOME_PAGE("home-page"),
	ABOUT_PAGE("about-dashboard"),
	MATH_PAGE("math"),
	COMMENTARY_PAGE("commentary"),
	DASHBOARD_PAGE("dashboard"),
	CHART_INFO_PAGE("chart-info"),
	DOWNLOAD_PAGE("download-data");
	
	public final String pageName;
	
	private JspPageNames(String name) {
		this.pageName = name;
	}
	
	@Override
	public String toString() {
		return this.pageName;
	}
}
