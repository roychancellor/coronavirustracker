package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartCsvHeaders;
import com.royware.corona.dashboard.enums.charts.ChartTypes;
import com.royware.corona.dashboard.enums.dashstats.DashStatsTypes;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigStore;
import com.royware.corona.dashboard.interfaces.charts.IChartListStore;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.dashboard.IDashStatsStore;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;
import com.royware.corona.dashboard.model.dashboard.DashboardChartData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;

@Component
public class DashboardChartServiceImpl implements DashboardChartService {
	@Autowired private IChartListStore chartListStore;
	@Autowired private IChartConfigStore chartConfigStore;
	@Autowired private IDashStatsStore dashStatsStore;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardChartServiceImpl.class);
	
	@Override
	public <T extends CanonicalCaseDeathData> List<DashboardChart> makeAllDashboardChartsAndStats(
			List<T> dataList, String region, Integer regionPopulation, DashboardStatistics dashStats) {
		
		List<DashboardChart> dashboardList = new ArrayList<>();
		
		boolean isNotWorld = true;
		if(region.length() == 3 && !region.equalsIgnoreCase("USA")) {
			isNotWorld = false;
		}
		
		log.info("Making all chart data lists");
		////////// CHART DATA LISTS - CASES //////////
		log.debug("Making all the chart data lists for CASES");
		
		List<List<Map<Object, Object>>> chartDataCasesByTime = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.CASES_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);
		List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.CASES_CHANGE_VERSUS_CASES, dataList, regionPopulation);
		List<List<Map<Object, Object>>> chartDataTotalCurrentCases = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.CASES_AS_FRAC_OF_POP_VERSUS_TIME, dataList, regionPopulation);
		
		////////// CHART DATA LISTS - DEATHS /////////
		log.debug("Making all the chart data lists for DEATHS");
		List<List<Map<Object, Object>>> chartDataDeathsByTime = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.DEATHS_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);

		////////// CHART DATA LISTS - VACCINATIONS /////////
		List<List<Map<Object, Object>>> chartDataVaccByTime = null;
		if(isNotWorld) {
			log.debug("Making all the chart data lists for VACCINATIONS");
			chartDataVaccByTime = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.VACC_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);
		}

		////////// CHART DATA LISTS - HOSPITALIZATIONS /////////
		List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime = null;
		if(isNotWorld) {
			log.debug("Making all the chart data lists for HOSPITALIZATIONS");
			chartDataCurrentHospitalizationsByTime = chartListStore
				.produceChartListFromRegionData(
						ChartTypes.HOSP_NOW_VERSUS_TIME, dataList, regionPopulation);
		}

		////////// DASHBOARD TABLE STATISTICS ///////////
		log.info("Making all the GENERAL DASHBOARD STATISTICS FOR REGION");
				
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_REGION_CASES, dashStats, dataList, chartDataCasesByTime, regionPopulation);
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_REGION_CASES_MOVING_SUM, dashStats, dataList, chartDataTotalCurrentCases, regionPopulation);
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_REGION_DEATHS, dashStats, dataList, chartDataDeathsByTime, regionPopulation);
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_REGION_DEATHS_MOVING_SUM, dashStats, dataList, chartDataDeathsByTime, regionPopulation);
		dashStats = dashStatsStore.produceDashboardStatsForType(
				DashStatsTypes.DASHSTATS_REGION_VACC, dashStats, dataList, chartDataVaccByTime, regionPopulation);
		
		if(isNotWorld) {
			log.debug("Making all the DASHBOARD STATISTICS FOR REGION - BY PROPORTION OF POPULATION");
			dashStats = dashStatsStore.produceDashboardStatsForType(
					DashStatsTypes.DASHSTATS_US_REGIONS_PROPORTIONS, dashStats, dataList, null, regionPopulation);
		}
		
		////////// CHART CONFIGURATION - CASES ///////////
		log.debug("Configuring all the charts for CASES...");
		DashboardChartConfig chartConfigCasesByTime = chartConfigStore
				.produceChartConfigFromList(
						ChartTypes.CASES_DAILY_AND_TOTAL_VERSUS_TIME, chartDataCasesByTime, region);
		DashboardChartConfig chartConfigTotalCurrentCases = chartConfigStore
				.produceChartConfigFromList(
						ChartTypes.CASES_AS_FRAC_OF_POP_VERSUS_TIME, chartDataTotalCurrentCases, region);
		DashboardChartConfig chartConfigRateOfCasesVersusCases = chartConfigStore
				.produceChartConfigFromList(
						ChartTypes.CASES_CHANGE_VERSUS_CASES, chartDataChangeOfCasesByCases, region);
		
		////////// CHART CONFIGURATION - DEATHS ///////////
		log.debug("Configuring all the charts for DEATHS...");
		DashboardChartConfig chartConfigDeathsByTime = chartConfigStore
				.produceChartConfigFromList(
						ChartTypes.DEATHS_DAILY_AND_TOTAL_VERSUS_TIME, chartDataDeathsByTime, region);
		
		////////// CHART CONFIGURATION - VACCINATIONS ///////////
		DashboardChartConfig chartConfigVaccByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for VACCINATIONS...");
			chartConfigVaccByTime = chartConfigStore
					.produceChartConfigFromList(
							ChartTypes.VACC_DAILY_AND_TOTAL_VERSUS_TIME, chartDataVaccByTime, region);
		}
		
		////////// CHART CONFIGURATION - HOSPITALIZATIONS ///////////
		DashboardChartConfig chartConfigCurrentHospitalizationsByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for HOSPITALIZATIONS...");
			chartConfigCurrentHospitalizationsByTime = chartConfigStore
					.produceChartConfigFromList(
							ChartTypes.HOSP_NOW_VERSUS_TIME, chartDataCurrentHospitalizationsByTime, region);
		}
		
		//////// WRITE TO LIST OF DASHBOARD CONFIGURATION OBJECTS ////////
		log.debug("Writing all the configurations to the dashboardList...");
		///// CASES /////
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataCasesByTime)
					.withCsvHeader(ChartCsvHeaders.CASES_TIME_SERIES.getName())
					.build())
				.setChartConfig(chartConfigCasesByTime)
				.setRegion(region)
				.build());
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataTotalCurrentCases)
					.withCsvHeader(ChartCsvHeaders.CASES_TOTAL_CURRENT.getName())
					.build())
				.setChartConfig(chartConfigTotalCurrentCases)
				.setRegion(region)
				.build());
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataChangeOfCasesByCases)
					.withCsvHeader(ChartCsvHeaders.CASES_CHG_BY_CASES.getName())
					.build())
				.setChartConfig(chartConfigRateOfCasesVersusCases)
				.setRegion(region)
				.build());

		///// DEATHS /////
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataDeathsByTime)
					.withCsvHeader(ChartCsvHeaders.DEATHS_TIME_SERIES.getName())
					.build())
				.setChartConfig(chartConfigDeathsByTime)
				.setRegion(region)
				.build());
		
		if(isNotWorld) {
			///// HOSPITALIZATIONS /////
			dashboardList.add(new DashboardChart.Builder()
					.setChartData(
						new DashboardChartData.Builder()
						.withChartDataLists(chartDataCurrentHospitalizationsByTime)
						.withCsvHeader(ChartCsvHeaders.HOSP_CURRENT.getName())
						.build())
					.setChartConfig(chartConfigCurrentHospitalizationsByTime)
					.setRegion(region)
					.build());
			
			///// VACCINATIONS /////
			dashboardList.add(new DashboardChart.Builder()
					.setChartData(
						new DashboardChartData.Builder()
						.withChartDataLists(chartDataVaccByTime)
						.withCsvHeader(ChartCsvHeaders.VACC_TIME_SERIES.getName())
						.build())
					.setChartConfig(chartConfigVaccByTime)
					.setRegion(region)
					.build());
		}

		return dashboardList;
	}
}
