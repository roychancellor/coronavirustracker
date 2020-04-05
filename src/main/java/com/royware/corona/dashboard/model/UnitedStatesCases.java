package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.royware.corona.dashboard.interfaces.CanonicalCases;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitedStatesCases implements CanonicalCases {
	@JsonProperty("date") private int date;
	@JsonProperty("positive") private int totalPositiveCases;
	@JsonProperty("negative") private int totalNegativeCases;
	@JsonProperty("death") private int totalDeaths;
	@JsonProperty("state") private String regionString;
	
	public UnitedStatesCases() {
		super();
	}

	public int getDate() {
		return date;
	}

	public int getTotalPositiveCases() {
		return totalPositiveCases;
	}

	public void setTotalPositiveCases(int positive) {
		this.totalPositiveCases = positive;
	}

	public int getTotalNegativeCases() {
		return totalNegativeCases;
	}

	public void setTotalNegativeCases(int negative) {
		this.totalNegativeCases = negative;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int death) {
		this.totalDeaths = death;
	}

	public String getRegionAbbrev() {
		return regionString;
	}

	public void setRegionAbbrev(String regionAbbrev) {
		this.regionString = regionAbbrev;
	}

	@Override
	public String toString() {
		return "UnitedStatesCases [date=" + date + ", totalPositiveCases=" + totalPositiveCases
				+ ", totalNegativeCases=" + totalNegativeCases + ", totalDeaths=" + totalDeaths + ", regionString="
				+ regionString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
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
		UnitedStatesCases other = (UnitedStatesCases) obj;
		if (date != other.date)
			return false;
		if (regionString == null) {
			if (other.regionString != null)
				return false;
		} else if (!regionString.equals(other.regionString))
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
