package com.royware.corona.dashboard.model.data;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldRecordsEuroCDC {
	@JsonProperty("records") private WorldData[] records;

	public WorldRecordsEuroCDC() {
		super();
	}

	public WorldData[] getRecords() {
		return records;
	}

	public void setRecords(WorldData[] records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "WorldRecords [records=" + Arrays.toString(records) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(records);
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
		WorldRecordsEuroCDC other = (WorldRecordsEuroCDC) obj;
		if (!Arrays.equals(records, other.records))
			return false;
		return true;
	}
}
