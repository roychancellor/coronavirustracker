package com.royware.corona.dashboard.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.WorldCases;
import com.royware.corona.dashboard.model.UnitedStatesCases;

@Service
public interface ChartListDataService {
	public List<UnitedStatesCases> getAllUsData();
	public List<UnitedStatesCases> getAllUsDataExcludingState(String stateAbbreviation);
	public List<UnitedStatesCases> getSingleUsStateData(String stateAbbreviation);
	public List<WorldCases> getSingleNonUsCountryData(String countryName);
}
