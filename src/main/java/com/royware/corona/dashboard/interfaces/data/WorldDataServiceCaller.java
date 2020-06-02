package com.royware.corona.dashboard.interfaces.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.data.WorldData;

@Service
public interface WorldDataServiceCaller {
	public List<WorldData> getDataFromWorldSource(String cacheKey);
}
