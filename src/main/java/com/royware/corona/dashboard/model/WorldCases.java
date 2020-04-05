package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.royware.corona.dashboard.interfaces.CanonicalCases;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldCases implements CanonicalCases {
	@JsonProperty("dateRep") private String stringDate;
	@JsonProperty("cases") private int dailyNewCases;
	@JsonProperty("deaths") private int dailyNewDeaths;
	@JsonProperty("countryterritoryCode") private String regionAbbrev;
	@JsonProperty("popData2018") private long population2018;
	
	@JsonIgnore private int totalPositiveCases;
	@JsonIgnore private int totalNegativeCases;
	@JsonIgnore private int totalDeaths;
	
	public WorldCases() {
		super();
	}

	public String getStringDate() {
		return stringDate;
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
	@JsonIgnore
	public int getDate() {
		return Integer.parseInt(stringDate.replace("/", ""));
	}

	@Override
	@JsonIgnore
	public int getTotalPositiveCases() {
		return totalPositiveCases;
	}

	@Override
	@JsonIgnore
	public void setTotalPositiveCases(int positiveCases) {
		this.totalPositiveCases = positiveCases;
	}

	@Override
	@JsonIgnore
	public int getTotalNegativeCases() {
		return totalNegativeCases;
	}

	@Override
	@JsonIgnore
	public void setTotalNegativeCases(int negativeCases) {
		this.totalNegativeCases = negativeCases;
	}

	@Override
	@JsonIgnore
	public int getTotalDeaths() {
		return totalDeaths;
	}

	@Override
	@JsonIgnore
	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	@Override
	public String toString() {
		return "WorldCases [dateDDMMYYYY=" + stringDate + ", dailyNewCases=" + dailyNewCases + ", dailyNewDeaths="
				+ dailyNewDeaths + ", countryAbbrev=" + regionAbbrev + ", population2018=" + population2018 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dailyNewCases;
		result = prime * result + dailyNewDeaths;
		result = prime * result + (int) (population2018 ^ (population2018 >>> 32));
		result = prime * result + ((regionAbbrev == null) ? 0 : regionAbbrev.hashCode());
		result = prime * result + ((stringDate == null) ? 0 : stringDate.hashCode());
		result = prime * result + totalDeaths;
		result = prime * result + totalNegativeCases;
		result = prime * result + totalPositiveCases;
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
		if (dailyNewCases != other.dailyNewCases)
			return false;
		if (dailyNewDeaths != other.dailyNewDeaths)
			return false;
		if (population2018 != other.population2018)
			return false;
		if (regionAbbrev == null) {
			if (other.regionAbbrev != null)
				return false;
		} else if (!regionAbbrev.equals(other.regionAbbrev))
			return false;
		if (stringDate == null) {
			if (other.stringDate != null)
				return false;
		} else if (!stringDate.equals(other.stringDate))
			return false;
		if (totalDeaths != other.totalDeaths)
			return false;
		if (totalNegativeCases != other.totalNegativeCases)
			return false;
		if (totalPositiveCases != other.totalPositiveCases)
			return false;
		return true;
	}


}
