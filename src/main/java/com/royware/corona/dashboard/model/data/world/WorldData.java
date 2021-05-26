package com.royware.corona.dashboard.model.data.world;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldData implements ICanonicalCaseDeathData {
	@JsonProperty("dateRep") private String stringDate;
	@JsonProperty("year") private int year;
	@JsonProperty("month") private int month;
	@JsonProperty("day") private int day;
	@JsonProperty("cases_weekly") private int dailyNewCases;
	@JsonProperty("deaths_weekly") private int dailyNewDeaths;
	@JsonProperty("countryterritoryCode") private String regionString;
	@JsonProperty("popData2018") private long population;
	
	@JsonIgnore private int totalPositiveCases;
	@JsonIgnore private int totalNegativeCases;
	@JsonIgnore private int totalDeaths;
	@JsonIgnore private LocalDate dateChecked;
	@JsonIgnore private int hospitalizedCurrently;
	@JsonIgnore private int hospitalizedCumulative;
	
	public WorldData() {
		super();
	}

	public LocalDate getDateChecked() {
		return LocalDate.of(year, month, day);
	}
	public void setDateChecked(int year, int month, int day) {
		this.dateChecked = LocalDate.of(year, month, day);
	}
		
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getStringDate() {
		return stringDate;
	}
	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public int getDailyNewCases() {
		return dailyNewCases;
	}
	public void setDailyNewCases(int dailyNewCases) {
		this.dailyNewCases = dailyNewCases;
	}

	public int getDailyNewDeaths() {
		return dailyNewDeaths;
	}
	public void setDailyNewDeaths(int dailyNewDeaths) {
		this.dailyNewDeaths = dailyNewDeaths;
	}

	@Override
	public String getRegionString() {
		return regionString;
	}
	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}

	@Override
	@JsonIgnore
	public int getDateInteger() {
		return Integer.parseInt(stringDate.replace("/", ""));
	}

	@Override
	@JsonIgnore
	public int getTotalPositiveCases() {
		return totalPositiveCases;
	}

	@Override
	@JsonIgnore
	public void setTotalPositiveCases(int positiveCases) {
		this.totalPositiveCases = positiveCases;
	}

	@Override
	@JsonIgnore
	public int getTotalNegativeCases() {
		return totalNegativeCases;
	}

	@Override
	@JsonIgnore
	public void setTotalNegativeCases(int negativeCases) {
		this.totalNegativeCases = negativeCases;
	}

	@Override
	@JsonIgnore
	public int getTotalDeaths() {
		return totalDeaths;
	}

	@Override
	@JsonIgnore
	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
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
	@JsonIgnore
	public int getTotalVaccCompleted() {
		return 0;
	}

	@Override
	@JsonIgnore
	public void setTotalVaccCompleted(int totalVaccCompleted) {		
	}

	@Override
	public String toString() {
		return "WorldCases [dateDDMMYYYY=" + stringDate + ", dailyNewCases=" + dailyNewCases + ", dailyNewDeaths="
				+ dailyNewDeaths + ", countryAbbrev=" + regionString + ", population2018=" + population + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dailyNewCases;
		result = prime * result + dailyNewDeaths;
		result = prime * result + (int) (population ^ (population >>> 32));
		result = prime * result + ((regionString == null) ? 0 : regionString.hashCode());
		result = prime * result + ((stringDate == null) ? 0 : stringDate.hashCode());
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
		WorldData other = (WorldData) obj;
		if (dailyNewCases != other.dailyNewCases)
			return false;
		if (dailyNewDeaths != other.dailyNewDeaths)
			return false;
		if (population != other.population)
			return false;
		if (regionString == null) {
			if (other.regionString != null)
				return false;
		} else if (!regionString.equals(other.regionString))
			return false;
		if (stringDate == null) {
			if (other.stringDate != null)
				return false;
		} else if (!stringDate.equals(other.stringDate))
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
