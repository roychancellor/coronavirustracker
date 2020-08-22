package com.royware.corona.dashboard.model.dashboard;

import org.springframework.stereotype.Service;

@Service
public class DashboardMeta {
	private String regionType;
	private int perCapitaBasis;

	public DashboardMeta() {
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public int getPerCapitaBasis() {
		return perCapitaBasis;
	}

	public void setPerCapitaBasis(int perCapitaBasis) {
		this.perCapitaBasis = perCapitaBasis;
	}
}
