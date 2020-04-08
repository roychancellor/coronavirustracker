package com.royware.corona.dashboard.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.ChartListDataService;

@Service
public class DataListBeanXXX {
	private List<UnitedStatesCases> usData;
	private List<WorldCases> worldData;
	
	public DataListBeanXXX(ChartListDataService dataService) {
		this.usData = dataService.getAllUsData();
		this.worldData = dataService.getAllWorldData();
	}

	public List<UnitedStatesCases> getUsData() {
		return usData;
	}

	public void setUsData(List<UnitedStatesCases> usData) {
		this.usData = usData;
	}

	public List<WorldCases> getWorldData() {
		return worldData;
	}

	public void setWorldData(List<WorldCases> worldData) {
		this.worldData = worldData;
	}
}
