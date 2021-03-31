package com.royware.corona.dashboard.model.data.us;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class VaccinationTimeSeriesCAN {
	@JsonProperty("actualsTimeseries") private VaccinationDataCAN[] actualsTimeSeries;

	public VaccinationTimeSeriesCAN() {
		super();
	}

	public VaccinationDataCAN[] getActualsTimeSeries() {
		return actualsTimeSeries;
	}

	public void setActualsTimeSeries(VaccinationDataCAN[] actualsTimeSeries) {
		this.actualsTimeSeries = actualsTimeSeries;
	}

	@Override
	public String toString() {
		return "VaccinationTimeSeriesCAN [actualsTimeSeries=" + Arrays.toString(actualsTimeSeries) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(actualsTimeSeries);
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
		VaccinationTimeSeriesCAN other = (VaccinationTimeSeriesCAN) obj;
		if (!Arrays.equals(actualsTimeSeries, other.actualsTimeSeries))
			return false;
		return true;
	}
}
