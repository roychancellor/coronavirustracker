package com.royware.corona.dashboard.model;

public class RegionData {
	private Regions abbreviation;
	private int population;
	private RegionLevels regionLevel;
	private String fullName;
	
	public RegionData(Regions abbreviation, int population, RegionLevels regionLevel, String fullName) {
		this.abbreviation = abbreviation;
		this.population = population;
		this.regionLevel = regionLevel;
		this.fullName = fullName;
	}
	
	public Regions getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(Regions abbreviation) {
		this.abbreviation = abbreviation;
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
