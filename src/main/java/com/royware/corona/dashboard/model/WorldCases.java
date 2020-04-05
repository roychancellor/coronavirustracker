package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldCases {
	@JsonProperty("dateRep") private int dateDDMMYYYY;
	@JsonProperty("cases") private int dailyNewCases;
	@JsonProperty("deaths") private int dailyNewDeaths;
	@JsonProperty("countryTerritoryCode") private String countryThreeLetterCode;
	@JsonProperty("popData2018") private long population2018;
	
	public WorldCases() {
		super();
	}

	public int getDateDDMMYYYY() {
		return dateDDMMYYYY;
	}

	public int getDailyNewCases() {
		return dailyNewCases;
	}

	public int getTotalPositiveCases() {
		return 1;
	}

	public int getDailyNewDeaths() {
		return dailyNewDeaths;
	}

	public int getTotalDeaths() {
		return 1;
	}

	public String getCountryThreeLetterCode() {
		return countryThreeLetterCode;
	}

	public long getPopulation2018() {
		return population2018;
	}

	@Override
	public String toString() {
		return "WorldCases [dateDDMMYYYY=" + dateDDMMYYYY + ", dailyNewCases=" + dailyNewCases + ", dailyNewDeaths="
				+ dailyNewDeaths + ", countryAbbrev=" + countryThreeLetterCode + ", population2018=" + population2018 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryThreeLetterCode == null) ? 0 : countryThreeLetterCode.hashCode());
		result = prime * result + dailyNewCases;
		result = prime * result + dailyNewDeaths;
		result = prime * result + dateDDMMYYYY;
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
		if (countryThreeLetterCode == null) {
			if (other.countryThreeLetterCode != null)
				return false;
		} else if (!countryThreeLetterCode.equals(other.countryThreeLetterCode))
			return false;
		if (dailyNewCases != other.dailyNewCases)
			return false;
		if (dailyNewDeaths != other.dailyNewDeaths)
			return false;
		if (dateDDMMYYYY != other.dateDDMMYYYY)
			return false;
		if (population2018 != other.population2018)
			return false;
		return true;
	}

}
