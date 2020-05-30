package com.royware.corona.dashboard.interfaces.dashboard;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public interface DashboardConfigService {
	public boolean populateDashboardModelMap(String region, ModelMap map);
}
