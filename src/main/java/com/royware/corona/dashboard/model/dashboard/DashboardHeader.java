package com.royware.corona.dashboard.model.dashboard;

import org.springframework.stereotype.Service;

@Service
public class DashboardHeader {
	private String fullRegion;
	private int population;
	
	public DashboardHeader() {
	}

	public String getFullRegion() {
		return fullRegion;
	}

	public void setFullRegion(String fullRegion) {
		this.fullRegion = fullRegion;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	
}
