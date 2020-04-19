package com.royware.corona.dashboard.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface CanonicalCases {
	public int getDate();
	public int getTotalPositiveCases();
	public void setTotalPositiveCases(int positive);
	public int getTotalNegativeCases();
	public void setTotalNegativeCases(int negative);
	public int getTotalDeaths();
	public void setTotalDeaths(int death);
	public String getRegionAbbrev();
}
