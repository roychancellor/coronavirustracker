package com.royware.corona.dashboard.model.data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UnitedStatesData implements CanonicalData {
	@JsonProperty("date") private int dateInteger;
	@JsonProperty("dateChecked") private String dateTimeString;
	@JsonProperty("positive") private int totalPositiveCases;
	@JsonProperty("negative") private int totalNegativeCases;
	@JsonProperty("posNeg") private int totalPositivePlusNegative;
	@JsonProperty("death") private int totalDeaths;
	@JsonProperty("pending") private int pendingTests;
	@JsonProperty("state") private String regionString;
	
	@JsonIgnore private LocalDate dateChecked;
			
	public UnitedStatesData() {
		super();
	}

	public int getDateInteger() {
		return dateInteger;
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

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public int getTotalPositivePlusNegative() {
		return totalPositivePlusNegative;
	}

	public void setTotalPositivePlusNegative(int totalPositivePlusNegative) {
		this.totalPositivePlusNegative = totalPositivePlusNegative;
	}

	public int getPendingTests() {
		return pendingTests;
	}

	public void setPendingTests(int pendingTests) {
		this.pendingTests = pendingTests;
	}

	public String getDateTimeString() {
		return dateTimeString;
	}

	public void setDateTimeString(String dateTimeChecked) {
		this.dateChecked = LocalDate.of(Integer.parseInt(dateTimeChecked.substring(0,4)),
				Integer.parseInt(dateTimeChecked.substring(5,7)), Integer.parseInt(dateTimeChecked.substring(8,10)));
		this.dateTimeString = dateTimeChecked;
	}

	public LocalDate getDateChecked() {
		return dateChecked;
	}

	public void setDateChecked(LocalDate dateChecked) {
		this.dateChecked = LocalDate.of(Integer.parseInt(dateTimeString.substring(0,4)),
				Integer.parseInt(dateTimeString.substring(5,7)), Integer.parseInt(dateTimeString.substring(8,10)));
	}

	@Override
	public String toString() {
		return "UnitedStatesCases [date=" + dateInteger + ", totalPositiveCases=" + totalPositiveCases
				+ ", totalNegativeCases=" + totalNegativeCases + ", totalDeaths=" + totalDeaths + ", regionString="
				+ regionString + ", dateTimeString=" + dateTimeString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dateInteger;
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
		UnitedStatesData other = (UnitedStatesData) obj;
		if (dateInteger != other.dateInteger)
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