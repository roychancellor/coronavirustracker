package com.royware.corona.dashboard.interfaces.chartlist;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Service
public interface IChartListStore {
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> produceChartListFromRegionData(
			ChartTypes type,
			List<T> regionDataList,
			int regionPopulation);
}
