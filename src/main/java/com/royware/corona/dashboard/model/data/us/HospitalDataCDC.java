package com.royware.corona.dashboard.model.data.us;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class HospitalDataCDC {

	@JsonProperty("state") private String regionString;
	@JsonProperty("date") private String dateTimeString;
	@JsonProperty("inpatient_beds_used_covid") private int totalBedsCovidCurrently;
	
	@JsonIgnore private DatesCDC_CovActNow datesCDC = new DatesCDC_CovActNow();	
	
	public HospitalDataCDC() {
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
	public int getTotalBedsCovidCurrently() {
		return totalBedsCovidCurrently;
	}
	public void setTotalBedsCovidCurrently(int totalBedsCovidCurrently) {
		this.totalBedsCovidCurrently = totalBedsCovidCurrently;
	}
	public DatesCDC_CovActNow getDatesCDC() {
		return datesCDC;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTimeString == null) ? 0 : dateTimeString.hashCode());
		result = prime * result + ((datesCDC == null) ? 0 : datesCDC.hashCode());
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
		result = prime * result + totalBedsCovidCurrently;
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
		HospitalDataCDC other = (HospitalDataCDC) obj;
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
		if (totalBedsCovidCurrently != other.totalBedsCovidCurrently)
			return false;
		return true;
	}
}
