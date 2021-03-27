package com.royware.corona.dashboard.model.data.us;

import java.time.LocalDate;

import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.interfaces.model.CanonicalHospitalData;

public class UnitedStatesData implements CanonicalCaseDeathData, CanonicalHospitalData {
	private int dateInteger;
	private int totalPositiveCases;
	private int totalNegativeCases;
	private int totalPositivePlusNegative;
	private int totalDeaths;
	private int pendingTests;
	private String regionString;
	private int hospitalizedCurrently;
	private int hospitalizedCumulative;
	private LocalDate dateChecked;
	private String dateTimeString;
	
	public UnitedStatesData() {
		super();
	}

	public int getDateInteger() {
		return dateInteger;
	}

	public void setDateInteger(int dateInteger) {
		this.dateInteger = dateInteger;
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
		this.dateTimeString = dateTimeString;
	}

	public LocalDate getDateChecked() {
		return dateChecked;
	}

	public void setDateChecked(LocalDate dateChecked) {
		this.dateChecked = dateChecked;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateChecked == null) ? 0 : dateChecked.hashCode());
		result = prime * result + dateInteger;
		result = prime * result + ((dateTimeString == null) ? 0 : dateTimeString.hashCode());
		result = prime * result + hospitalizedCumulative;
		result = prime * result + hospitalizedCurrently;
		result = prime * result + pendingTests;
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
		result = prime * result + totalDeaths;
		result = prime * result + totalNegativeCases;
		result = prime * result + totalPositiveCases;
		result = prime * result + totalPositivePlusNegative;
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
		if (dateChecked == null) {
			if (other.dateChecked != null)
				return false;
		} else if (!dateChecked.equals(other.dateChecked))
			return false;
		if (dateInteger != other.dateInteger)
			return false;
		if (dateTimeString == null) {
			if (other.dateTimeString != null)
				return false;
		} else if (!dateTimeString.equals(other.dateTimeString))
			return false;
		if (hospitalizedCumulative != other.hospitalizedCumulative)
			return false;
		if (hospitalizedCurrently != other.hospitalizedCurrently)
			return false;
		if (pendingTests != other.pendingTests)
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
		if (totalPositivePlusNegative != other.totalPositivePlusNegative)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnitedStatesData [dateInteger=" + dateInteger + ", totalPositiveCases=" + totalPositiveCases
				+ ", totalNegativeCases=" + totalNegativeCases + ", totalPositivePlusNegative="
				+ totalPositivePlusNegative + ", totalDeaths=" + totalDeaths + ", pendingTests=" + pendingTests
				+ ", regionString=" + regionString + ", hospitalizedCurrently=" + hospitalizedCurrently
				+ ", hospitalizedCumulative=" + hospitalizedCumulative + ", dateChecked=" + dateChecked
				+ ", dateTimeString=" + dateTimeString + "]";
	}

	
}
