package com.royware.corona.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldData {
	private int date;
	private int positive;
	private int negative;
	private int death;
	
	public WorldData() {
		super();
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
	}

	@Override
	public String toString() {
		return "UnitedStatesData [date=" + date + ", positive=" + positive + ", negative=" + negative + ", death="
				+ death + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + death;
		result = prime * result + negative;
		result = prime * result + positive;
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
		if (date != other.date)
			return false;
		if (death != other.death)
			return false;
		if (negative != other.negative)
			return false;
		if (positive != other.positive)
			return false;
		return true;
	}
	
	
	
}
