package com.royware.corona.dashboard.interfaces.charts;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Service
public interface IChartList {
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeList(List<T> regionDataList);
}
