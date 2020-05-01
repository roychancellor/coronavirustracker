package com.royware.corona.dashboard.model;

import com.royware.corona.dashboard.enums.RegionLevels;

public class RegionData {
	private int population;
	private RegionLevels regionLevel;
	private String fullName;
	
	public RegionData(int population, RegionLevels regionLevel, String fullName) {
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
	public RegionLevels getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(RegionLevels regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
