package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.model.UnitedStatesData;

@Service
public interface DashboardService {
	public UnitedStatesData[] getAllUsData();
}
