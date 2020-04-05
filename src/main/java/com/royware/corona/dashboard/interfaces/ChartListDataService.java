package com.royware.corona.dashboard.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldCases;

@Service
public interface ChartListDataService {
	public List<UnitedStatesCases> getAllUsData();
	public List<UnitedStatesCases> getSingleUsStateData(String stateAbbreviation);
	public List<UnitedStatesCases> getAllUsDataExcludingState(String stateAbbreviation);
	public List<WorldCases> getAllWorldData();
	public List<WorldCases> getSingleNonUsCountryData(String countryName);
}
