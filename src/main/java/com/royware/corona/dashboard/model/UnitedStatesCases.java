package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitedStatesCases {
	@JsonProperty("date") private int dateYYYMMDD;
	@JsonProperty("positive") private int totalPositiveCases;
	@JsonProperty("negative") private int totalNegativeCases;
	@JsonProperty("death") private int totalDeaths;
	@JsonProperty("state") private String stateAbbrev;
	
	public UnitedStatesCases() {
		super();
	}

	public int getDateYYYMMDD() {
		return dateYYYMMDD;
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

	/**
	 * @return the stateAbbrev
	 */
	public String getStateAbbrev() {
		return stateAbbrev;
	}

	/**
	 * @param stateAbbrev the stateAbbrev to set
	 */
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	@Override
	public String toString() {
		return "UnitedStatesCases [date=" + dateYYYMMDD + ", positive=" + totalPositiveCases + ", negative=" + totalNegativeCases + ", death="
				+ totalDeaths + ", stateAbbrev=" + stateAbbrev + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dateYYYMMDD;
		result = prime * result + totalDeaths;
		result = prime * result + totalNegativeCases;
		result = prime * result + totalPositiveCases;
		result = prime * result + ((stateAbbrev == null) ? 0 : stateAbbrev.hashCode());
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
		if (dateYYYMMDD != other.dateYYYMMDD)
			return false;
		if (totalDeaths != other.totalDeaths)
			return false;
		if (totalNegativeCases != other.totalNegativeCases)
			return false;
		if (totalPositiveCases != other.totalPositiveCases)
			return false;
		if (stateAbbrev == null) {
			if (other.stateAbbrev != null)
				return false;
		} else if (!stateAbbrev.equals(other.stateAbbrev))
			return false;
		return true;
	}

}
