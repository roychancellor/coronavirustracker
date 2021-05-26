package com.royware.corona.dashboard.interfaces.model;

public interface ICanonicalHospitalData extends ICanonicalVaccinationData {
	public int getHospitalizedCurrently();
	public void setHospitalizedCurrently(int hospitalizedCurrently);
	public int getHospitalizedCumulative();
	public void setHospitalizedCumulative(int hospitalizedCumulative);
}
