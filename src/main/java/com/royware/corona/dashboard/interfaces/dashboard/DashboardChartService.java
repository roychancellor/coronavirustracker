package com.royware.corona.dashboard.interfaces.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Service
public interface DashboardChartService {
	public <T extends CanonicalData> List<DashboardChart> makeAllDashboardCharts(
			List<T> caseList, String region, Integer regionPopulation, DashboardStatistics dashStats);
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats);
	public void makeDashboardStatsForRegion(DashboardStatistics dashStats,
			List<List<Map<Object, Object>>> chartDataCasesByTime,
			List<List<Map<Object, Object>>> chartDataRateOfCasesByTime,
			List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime,
			List<List<Map<Object, Object>>> chartDataDeathsByTime,
			List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime,
			List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime);
	public <T extends CanonicalData> void makeDashboardStatsForUSRegionsByTesting(List<T> dataList, DashboardStatistics dashStats);
	
	public DashboardChartConfig chartConfigCasesByTime(String region, List<List<Map<Object, Object>>> chartDataCasesByTime);
	public DashboardChartConfig chartConfigRateOfChangeOfCases(String region, List<List<Map<Object, Object>>> chartDataRateOfCasesByTime);
	public DashboardChartConfig chartConfigAccelerationOfCases(String region, List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime);
	public DashboardChartConfig chartConfigTotalCurrentCases(String region, List<List<Map<Object, Object>>> chartDataTotalCurrentCases);
	public DashboardChartConfig chartConfigRateOfCasesVersusCases(String region, List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases);
	
	public DashboardChartConfig chartConfigDeathsByTime(String region, List<List<Map<Object, Object>>> chartDataDeathsByTime);
	public DashboardChartConfig chartConfigRateOfChangeOfDeaths(String region, List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime);
	public DashboardChartConfig chartConfigAccelerationOfDeaths(String region, List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime);
	public DashboardChartConfig chartConfigRateOfDeathsVersusDeaths(String region, List<List<Map<Object, Object>>> chartDataChangeOfDeathsByDeaths);
	
	public DashboardChartConfig chartConfigRatioOfCasesToTestsByTime(String region, List<List<Map<Object, Object>>> chartDataRatioOfCasesToTestsByTime);
	public DashboardChartConfig chartConfigTestsByTime(String region, List<List<Map<Object, Object>>> chartDataTestsByTime);
	
	public DashboardChartConfig chartConfigCurrentHospitalizationsByTime(String region, List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime);
	public DashboardChartConfig chartConfigCumulativeHospitalizationsByTime(String region, List<List<Map<Object, Object>>> chartDataCumulativeHospitalizationsByTime);
}
