package com.royware.corona.dashboard.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class HospitalDataCDC {

	@JsonProperty("state") private String regionString;
	@JsonProperty("date") private String dateTimeString;
	@JsonProperty("inpatient_beds_used_covid") private int totalBedsCovidCurrently;
	
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
	public int getTotalBedsCovidCurrently() {
		return totalBedsCovidCurrently;
	}
	public void setTotalBedsCovidCurrently(int totalBedsCovidCurrently) {
		this.totalBedsCovidCurrently = totalBedsCovidCurrently;
	}
}
