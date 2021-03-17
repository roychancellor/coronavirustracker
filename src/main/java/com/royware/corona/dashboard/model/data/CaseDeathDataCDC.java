package com.royware.corona.dashboard.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CaseDeathDataCDC {

	@JsonProperty("state") private String regionString;
	@JsonProperty("submission_date") private String dateTimeString;
	@JsonProperty("tot_cases") private int totalPositiveCases;
	@JsonProperty("tot_death") private int totalDeaths;
	
	@JsonIgnore private DatesCDC datesCDC = new DatesCDC();	
	
	public CaseDeathDataCDC() {
		super();
	}
	public String getRegionString() {
		return regionString;
	}
	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}
	public String getDateTimeString() {
		return dateTimeString;
	}
	public void setDateTimeString(String dateTimeString) {
		this.dateTimeString = dateTimeString;
		//datesCDC.setDateFields(dateTimeString);
	}
	public int getTotalPositiveCases() {
		return totalPositiveCases;
	}
	public void setTotalPositiveCases(int totalPositiveCases) {
		this.totalPositiveCases = totalPositiveCases;
	}
	public int getTotalDeaths() {
		return totalDeaths;
	}
	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}
	public DatesCDC getDatesCDC() {
		return datesCDC;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTimeString == null) ? 0 : dateTimeString.hashCode());
		result = prime * result + ((datesCDC == null) ? 0 : datesCDC.hashCode());
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
		result = prime * result + totalDeaths;
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
		CaseDeathDataCDC other = (CaseDeathDataCDC) obj;
		if (dateTimeString == null) {
			if (other.dateTimeString != null)
				return false;
		} else if (!dateTimeString.equals(other.dateTimeString))
			return false;
		if (datesCDC == null) {
			if (other.datesCDC != null)
				return false;
		} else if (!datesCDC.equals(other.datesCDC))
			return false;
		if (regionString == null) {
			if (other.regionString != null)
				return false;
		} else if (!regionString.equals(other.regionString))
			return false;
		if (totalDeaths != other.totalDeaths)
			return false;
		if (totalPositiveCases != other.totalPositiveCases)
			return false;
		return true;
	}
}
