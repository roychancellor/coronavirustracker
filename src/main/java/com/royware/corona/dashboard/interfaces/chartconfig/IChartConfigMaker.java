package com.royware.corona.dashboard.interfaces.chartconfig;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

@Service
public interface IChartConfigMaker {
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region);
}
