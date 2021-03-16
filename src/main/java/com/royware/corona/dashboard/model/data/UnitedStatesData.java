package com.royware.corona.dashboard.model.data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.interfaces.model.UsHospitalData;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UnitedStatesData implements CanonicalData, UsHospitalData {
	@JsonProperty("state") private String regionString;
	@JsonProperty("date") private String dateTimeString;
	@JsonProperty("cases") private int totalPositiveCases;
	@JsonProperty("deaths") private int totalDeaths;
	
	@JsonIgnore private int totalNegativeCases;
	@JsonIgnore private int dateInteger;
	@JsonIgnore private LocalDate dateChecked;
	@JsonIgnore private int pendingTests;
	@JsonIgnore private int totalPositivePlusNegative;
	@JsonIgnore private int hospitalizedCurrently;
	@JsonIgnore private int hospitalizedCumulative;
			
	public UnitedStatesData() {
		super();
	}

	public int getDateInteger() {
		return dateInteger;
	}

	public void setDateInteger(int dateInteger) {
		this.dateInteger = dateInteger;
		this.dateChecked = LocalDate.of(dateInteger/10000, (dateInteger % 10000)/100, dateInteger % 100);
		String month = (dateInteger % 10000)/100 < 10 ? "0" + String.valueOf((dateInteger % 10000)/100) : String.valueOf((dateInteger % 10000)/100);
		String day = dateInteger % 100 < 10 ? "0" + String.valueOf(dateInteger % 100) : String.valueOf(dateInteger % 100);
		this.dateTimeString = String.valueOf(dateInteger/10000) + "-" + month + "-" + day + "T00:00:00Z";
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

	public void setDateTimeString(String dateTimeString) {
		this.dateChecked = LocalDate.of(Integer.parseInt(dateTimeString.substring(0,4)),
				Integer.parseInt(dateTimeString.substring(5,7)), Integer.parseInt(dateTimeString.substring(8,10)));
		this.dateTimeString = dateTimeString;
	}

	public LocalDate getDateChecked() {
		return dateChecked;
	}

	public void setDateChecked(LocalDate dateChecked) {
		this.dateChecked = LocalDate.of(Integer.parseInt(dateTimeString.substring(0,4)),
				Integer.parseInt(dateTimeString.substring(5,7)), Integer.parseInt(dateTimeString.substring(8,10)));
	}

	public int getHospitalizedCurrently() {
		return hospitalizedCurrently;
	}

	public void setHospitalizedCurrently(int hospitalizedCurrently) {
		this.hospitalizedCurrently = hospitalizedCurrently;
	}

	public int getHospitalizedCumulative() {
		return hospitalizedCumulative;
	}

	public void setHospitalizedCumulative(int hospitalizedCumulative) {
		this.hospitalizedCumulative = hospitalizedCumulative;
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
