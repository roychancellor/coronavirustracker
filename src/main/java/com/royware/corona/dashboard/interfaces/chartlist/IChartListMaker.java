package com.royware.corona.dashboard.interfaces.chartlist;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Service
public interface IChartListMaker {
	//CASES WILL ALWAYS START WITH INDEX = 0 --> DAY = 0
	//DEATHS WILL HAVE A VARIABLE-INDEX --> DAY = 0 (DEATHS LAG CASES AND VARIES AMONG REGIONS)
	//TESTS WILL ALWAYS START WITH INDEX = 0 --> DAY = 0
	//HOSPITALIZATIONS WILL HAVE A VARIABLE-INDEX --> DAY = 0 (DATA NOT ALWAYS AVAILABLE AMONG REGIONS)
	
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int regionPopulation);
}
