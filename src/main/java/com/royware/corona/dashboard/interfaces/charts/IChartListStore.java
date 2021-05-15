package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Service
public interface IChartListStore {
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> produceChartListFromRegionData(
			ChartTypes type, List<T> regionDataList);
}
