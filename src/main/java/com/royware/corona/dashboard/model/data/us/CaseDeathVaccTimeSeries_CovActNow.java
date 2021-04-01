package com.royware.corona.dashboard.model.data.us;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CaseDeathVaccTimeSeries_CovActNow {
	@JsonProperty("state") private String regionString;
	@JsonProperty("actualsTimeseries") private CaseDeathVaccData_CovidActNow[] actualsTimeSeries;

	public CaseDeathVaccTimeSeries_CovActNow() {
		super();
	}

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public CaseDeathVaccData_CovidActNow[] getActualsTimeSeries() {
		return actualsTimeSeries;
	}

	public void setActualsTimeSeries(CaseDeathVaccData_CovidActNow[] actualsTimeSeries) {
		this.actualsTimeSeries = actualsTimeSeries;
	}

	@Override
	public String toString() {
		return "CaseDeathVaccTimeSeries_CovActNow [regionString=" + regionString + ", actualsTimeSeries="
				+ Arrays.toString(actualsTimeSeries) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(actualsTimeSeries);
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
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
		CaseDeathVaccTimeSeries_CovActNow other = (CaseDeathVaccTimeSeries_CovActNow) obj;
		if (!Arrays.equals(actualsTimeSeries, other.actualsTimeSeries))
			return false;
		if (regionString == null) {
			if (other.regionString != null)
				return false;
		} else if (!regionString.equals(other.regionString))
			return false;
		return true;
	}
}
