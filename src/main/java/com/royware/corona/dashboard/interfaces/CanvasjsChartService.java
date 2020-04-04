package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public interface CanvasjsChartService {
	List<List<Map<Object, Object>>> getCanvasjsChartData(UnitedStatesData[] usd);
}
