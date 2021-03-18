package com.royware.corona.dashboard.interfaces.model;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public interface CanonicalCaseDeathData extends CanonicalHospitalData {
	public int getDateInteger();
	public LocalDate getDateChecked();
	public int getTotalPositiveCases();
	public void setTotalPositiveCases(int positive);
	public int getTotalNegativeCases();
	public void setTotalNegativeCases(int negative);
	public int getTotalDeaths();
	public void setTotalDeaths(int death);
	public String getRegionString();
}
