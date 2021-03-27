package com.royware.corona.dashboard.model.data.us;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CovidActNowActual {

	@JsonProperty("vaccinesDistributed") private int vaccDist;
	@JsonProperty("vaccinationsInitiated") private int vaccInit;
	@JsonProperty("vaccinationsCompleted") private int vaccComp;
	@JsonProperty("vaccinesAdministered") private int vaccAdmin;
	@JsonProperty("date") private String dateYYYY_MM_DD;
	
	@JsonIgnore private DatesCDC_CAN datesCDC = new DatesCDC_CAN();

	public CovidActNowActual() {
		super();
	}

	public int getVaccDist() {
		return vaccDist;
	}

	public void setVaccDist(int vaccDist) {
		this.vaccDist = vaccDist;
	}

	public int getVaccInit() {
		return vaccInit;
	}

	public void setVaccInit(int vaccInit) {
		this.vaccInit = vaccInit;
	}

	public int getVaccComp() {
		return vaccComp;
	}

	public void setVaccComp(int vaccComp) {
		this.vaccComp = vaccComp;
	}

	public int getVaccAdmin() {
		return vaccAdmin;
	}

	public void setVaccAdmin(int vaccAdmin) {
		this.vaccAdmin = vaccAdmin;
	}

	public String getDateYYYY_MM_DD() {
		return dateYYYY_MM_DD;
	}

	public void setDateYYYY_MM_DD(String dateYYYY_MM_DD) {
		this.dateYYYY_MM_DD = dateYYYY_MM_DD;
	}

	public DatesCDC_CAN getDatesCDC() {
		return datesCDC;
	}

	public void setDatesCDC(DatesCDC_CAN datesCDC) {
		this.datesCDC = datesCDC;
	}

	@Override
	public String toString() {
		return "CovidActNowActual [vaccDist=" + vaccDist + ", vaccInit=" + vaccInit + ", vaccComp=" + vaccComp
				+ ", vaccAdmin=" + vaccAdmin + ", dateYYYY_MM_DD=" + dateYYYY_MM_DD + ", datesCDC=" + datesCDC + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateYYYY_MM_DD == null) ? 0 : dateYYYY_MM_DD.hashCode());
		result = prime * result + ((datesCDC == null) ? 0 : datesCDC.hashCode());
		result = prime * result + vaccAdmin;
		result = prime * result + vaccComp;
		result = prime * result + vaccDist;
		result = prime * result + vaccInit;
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
		CovidActNowActual other = (CovidActNowActual) obj;
		if (dateYYYY_MM_DD == null) {
			if (other.dateYYYY_MM_DD != null)
				return false;
		} else if (!dateYYYY_MM_DD.equals(other.dateYYYY_MM_DD))
			return false;
		if (datesCDC == null) {
			if (other.datesCDC != null)
				return false;
		} else if (!datesCDC.equals(other.datesCDC))
			return false;
		if (vaccAdmin != other.vaccAdmin)
			return false;
		if (vaccComp != other.vaccComp)
			return false;
		if (vaccDist != other.vaccDist)
			return false;
		if (vaccInit != other.vaccInit)
			return false;
		return true;
	}	
}
