package com.royware.corona.dashboard.model.data.common;

import com.royware.corona.dashboard.enums.regions.RegionTypes;

public class RegionData {
	private int population;
	private RegionTypes regionLevel;
	private String fullName;
	
	public RegionData(int population, RegionTypes regionLevel, String fullName) {
		this.population = population;
		this.regionLevel = regionLevel;
		this.fullName = fullName;
	}
	
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public RegionTypes getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(RegionTypes regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
