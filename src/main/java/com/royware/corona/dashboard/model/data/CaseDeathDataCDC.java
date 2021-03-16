package com.royware.corona.dashboard.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CaseDeathDataCDC {

	@JsonProperty("state") private String regionString;
	@JsonProperty("submission_date") private String dateTimeString;
	@JsonProperty("tot_cases") private int totalPositiveCases;
	@JsonProperty("tot_death") private int totalDeaths;
	
	@JsonIgnore private DatesCDC datesCDC;	
	
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
		datesCDC.setDateFields(dateTimeString);
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
}
