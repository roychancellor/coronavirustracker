package com.royware.corona.dashboard.model.data.world;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldDataOWID {
	@JsonProperty("date") private String dateStringYYYY_MM_DD;
	@JsonProperty("total_cases") private long totalCases;
	@JsonProperty("new_cases") private long newCases;
	@JsonProperty("total_deaths") private long totalDeaths;
	@JsonProperty("new_deaths") private long newDeaths;

	public WorldDataOWID() {
		super();
	}

	public String getDateStringYYYY_MM_DD() {
		return dateStringYYYY_MM_DD;
	}

	public void setDateStringYYYY_MM_DD(String dateStringYYYY_MM_DD) {
		this.dateStringYYYY_MM_DD = dateStringYYYY_MM_DD;
	}

	public long getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(long totalCases) {
		this.totalCases = totalCases;
	}

	public long getNewCases() {
		return newCases;
	}

	public void setNewCases(long newCases) {
		this.newCases = newCases;
	}

	public long getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(long totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public long getNewDeaths() {
		return newDeaths;
	}

	public void setNewDeaths(long newDeaths) {
		this.newDeaths = newDeaths;
	}

	@Override
	public String toString() {
		return "WorldRecordsOurWorldInData [dateStringYYYY_MM_DD=" + dateStringYYYY_MM_DD + ", totalCases=" + totalCases
				+ ", newCases=" + newCases + ", totalDeaths=" + totalDeaths + ", newDeaths=" + newDeaths
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateStringYYYY_MM_DD == null) ? 0 : dateStringYYYY_MM_DD.hashCode());
		result = prime * result + (int) (newCases ^ (newCases >>> 32));
		result = prime * result + (int) (newDeaths ^ (newDeaths >>> 32));
		result = prime * result + (int) (totalCases ^ (totalCases >>> 32));
		result = prime * result + (int) (totalDeaths ^ (totalDeaths >>> 32));
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
		WorldDataOWID other = (WorldDataOWID) obj;
		if (dateStringYYYY_MM_DD == null) {
			if (other.dateStringYYYY_MM_DD != null)
				return false;
		} else if (!dateStringYYYY_MM_DD.equals(other.dateStringYYYY_MM_DD))
			return false;
		if (newCases != other.newCases)
			return false;
		if (newDeaths != other.newDeaths)
			return false;
		if (totalCases != other.totalCases)
			return false;
		if (totalDeaths != other.totalDeaths)
			return false;
		return true;
	}
}
