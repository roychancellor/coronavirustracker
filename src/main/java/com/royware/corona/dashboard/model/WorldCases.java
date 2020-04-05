package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.royware.corona.dashboard.interfaces.CanonicalCases;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldCases implements CanonicalCases {
	@JsonProperty("dateRep") private int date;
	@JsonProperty("cases") private int dailyNewCases;
	@JsonProperty("deaths") private int dailyNewDeaths;
	@JsonProperty("countryTerritoryCode") private String regionAbbrev;
	@JsonProperty("popData2018") private long population2018;
	
	private int totalPositiveCases;
	private int totalNegativeCases;
	private int totalDeaths;
	
	public WorldCases() {
		super();
	}

	public int getDate() {
		return date;
	}

	public int getDailyNewCases() {
		return dailyNewCases;
	}

	public int getDailyNewDeaths() {
		return dailyNewDeaths;
	}

	@Override
	public String getRegionAbbrev() {
		return regionAbbrev;
	}

	public long getPopulation2018() {
		return population2018;
	}

	@Override
	public int getTotalPositiveCases() {
		return totalPositiveCases;
	}

	@Override
	public void setTotalPositiveCases(int positiveCases) {
		this.totalPositiveCases = positiveCases;
	}

	@Override
	public int getTotalNegativeCases() {
		return totalNegativeCases;
	}

	@Override
	public void setTotalNegativeCases(int negativeCases) {
		this.totalNegativeCases = negativeCases;
	}

	@Override
	public int getTotalDeaths() {
		return totalDeaths;
	}

	@Override
	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	@Override
	public String toString() {
		return "WorldCases [dateDDMMYYYY=" + date + ", dailyNewCases=" + dailyNewCases + ", dailyNewDeaths="
				+ dailyNewDeaths + ", countryAbbrev=" + regionAbbrev + ", population2018=" + population2018 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionAbbrev == null) ? 0 : regionAbbrev.hashCode());
		result = prime * result + dailyNewCases;
		result = prime * result + dailyNewDeaths;
		result = prime * result + date;
		result = prime * result + (int) (population2018 ^ (population2018 >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldCases other = (WorldCases) obj;
		if (regionAbbrev == null) {
			if (other.regionAbbrev != null)
				return false;
		} else if (!regionAbbrev.equals(other.regionAbbrev))
			return false;
		if (dailyNewCases != other.dailyNewCases)
			return false;
		if (dailyNewDeaths != other.dailyNewDeaths)
			return false;
		if (date != other.date)
			return false;
		if (population2018 != other.population2018)
			return false;
		return true;
	}
}
