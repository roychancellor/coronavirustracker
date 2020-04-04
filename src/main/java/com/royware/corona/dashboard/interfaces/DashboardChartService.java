package com.royware.corona.dashboard.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashboardChartService {
	List<List<Map<Object, Object>>> getTotalCasesVersusTimeWithExponentialFit();
}