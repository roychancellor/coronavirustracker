package com.royware.corona.dashboard.interfaces.dashboard;

import org.springframework.stereotype.Service;
import com.royware.corona.dashboard.enums.dashstats.DashStatsTypes;

@Service
public interface IDashStatsFactory {
	public IDashStatsMaker create(DashStatsTypes statsType);
}
