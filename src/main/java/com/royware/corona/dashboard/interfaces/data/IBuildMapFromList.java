package com.royware.corona.dashboard.interfaces.data;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.model.data.UnitedStatesData;

public interface IBuildUnitedStatesDataList {
	public List<UnitedStatesData> buildList(Map<Integer, Object> source1, Map<Integer, Object> source2);
}
