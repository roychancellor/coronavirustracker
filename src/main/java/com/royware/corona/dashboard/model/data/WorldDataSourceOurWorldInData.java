package com.royware.corona.dashboard.model.data;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldDataSourceOurWorldInData {
	@JsonProperty("population") private long population;
	@JsonProperty("data") private WorldDataOWID[] data;

	public WorldDataSourceOurWorldInData() {
		super();
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public WorldDataOWID[] getData() {
		return data;
	}

	public void setData(WorldDataOWID[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WorldDataSourceOurWorldInData [population=" + population + ", data=" + Arrays.toString(data) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + (int) (population ^ (population >>> 32));
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
		WorldDataSourceOurWorldInData other = (WorldDataSourceOurWorldInData) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (population != other.population)
			return false;
		return true;
	}

}
