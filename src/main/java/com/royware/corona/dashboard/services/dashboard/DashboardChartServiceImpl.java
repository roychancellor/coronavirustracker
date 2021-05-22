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
import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigStore;
import com.royware.corona.dashboard.interfaces.charts.IChartListStore;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;
import com.royware.corona.dashboard.model.dashboard.DashboardChartData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigMakerUtilities;

@Component
public class DashboardChartServiceImpl implements DashboardChartService {
	@Autowired
	private IChartListStore chartListStore;
	
	@Autowired
	private IChartConfigStore chartConfigStore;
	
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardChartServiceImpl.class);
	
	@Override
	public <T extends CanonicalCaseDeathData> List<DashboardChart> makeAllDashboardCharts(
			List<T> dataList, String region, Integer regionPopulation, DashboardStatistics dashStats) {
		
		List<DashboardChart> dashboardList = new ArrayList<>();
		boolean isNotWorld = true;
		if(region.length() == 3 && !region.equalsIgnoreCase("USA")) {
			isNotWorld = false;
		}
		
		//TODO: Make the dashstats factory classes (factory and store)
		//TODO: Wire in the factory method calls in the different places
		
		log.info("Making all chart data lists");
		////////// CHART DATA LISTS - CASES //////////
		log.debug("Making all the chart data lists for CASES");
		
		List<List<Map<Object, Object>>> chartDataCasesByTime = chartListStore
				.produceChartListFromRegionData(ChartTypes.CASES_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);
		List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases = chartListStore
				.produceChartListFromRegionData(ChartTypes.CASES_CHANGE_VERSUS_CASES, dataList, regionPopulation);
		List<List<Map<Object, Object>>> chartDataTotalCurrentCases = chartListStore
				.produceChartListFromRegionData(ChartTypes.CASES_AS_FRAC_OF_POP_VERSUS_TIME, dataList, regionPopulation);
		
		////////// CHART DATA LISTS - DEATHS /////////
		log.debug("Making all the chart data lists for DEATHS");
		List<List<Map<Object, Object>>> chartDataDeathsByTime = chartListStore
				.produceChartListFromRegionData(ChartTypes.DEATHS_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);

		////////// CHART DATA LISTS - VACCINATIONS /////////
		List<List<Map<Object, Object>>> chartDataVaccByTime = null;
		if(isNotWorld) {
			log.debug("Making all the chart data lists for VACCINATIONS");
			chartDataVaccByTime = chartListStore
				.produceChartListFromRegionData(ChartTypes.VACC_DAILY_AND_TOTAL_VERSUS_TIME, dataList, regionPopulation);
		}

		////////// CHART DATA LISTS - HOSPITALIZATIONS /////////
		List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime = null;
		if(isNotWorld) {
			log.debug("Making all the chart data lists for HOSPITALIZATIONS");
			chartDataCurrentHospitalizationsByTime = chartListStore
				.produceChartListFromRegionData(ChartTypes.HOSP_NOW_VERSUS_TIME, dataList, regionPopulation);
		}

		////////// DASHBOARD TABLE STATISTICS ///////////
		log.debug("Making all the DASHBOARD STATISTICS FOR REGION - ORIGINAL");
		
		makeDashboardStatsForRegion(dashStats, regionPopulation, chartDataCasesByTime, chartDataTotalCurrentCases, chartDataDeathsByTime, chartDataVaccByTime);
		
		if(isNotWorld) {
			log.debug("Making all the DASHBOARD STATISTICS FOR REGION - BY TESTING");
			makeDashboardStatsForUSRegionsByTesting(dataList, dashStats);
		}
		
		////////// CHART CONFIGURATION - CASES ///////////
		log.debug("Configuring all the charts for CASES...");
		DashboardChartConfig chartConfigCasesByTime = chartConfigStore
				.produceChartConfigFromList(ChartTypes.CASES_DAILY_AND_TOTAL_VERSUS_TIME, chartDataCasesByTime, region);
		DashboardChartConfig chartConfigTotalCurrentCases = chartConfigStore
				.produceChartConfigFromList(ChartTypes.CASES_AS_FRAC_OF_POP_VERSUS_TIME, chartDataTotalCurrentCases, region);
		DashboardChartConfig chartConfigRateOfCasesVersusCases = chartConfigStore
				.produceChartConfigFromList(ChartTypes.CASES_CHANGE_VERSUS_CASES, chartDataChangeOfCasesByCases, region);
		
		////////// CHART CONFIGURATION - DEATHS ///////////
		log.debug("Configuring all the charts for DEATHS...");
		DashboardChartConfig chartConfigDeathsByTime = chartConfigStore
				.produceChartConfigFromList(ChartTypes.DEATHS_DAILY_AND_TOTAL_VERSUS_TIME, chartDataDeathsByTime, region);
		
		////////// CHART CONFIGURATION - VACCINATIONS ///////////
		DashboardChartConfig chartConfigVaccByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for VACCINATIONS...");
			chartConfigVaccByTime = chartConfigStore
					.produceChartConfigFromList(ChartTypes.VACC_DAILY_AND_TOTAL_VERSUS_TIME, chartDataVaccByTime, region);
		}
		
		////////// CHART CONFIGURATION - HOSPITALIZATIONS ///////////
		DashboardChartConfig chartConfigCurrentHospitalizationsByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for HOSPITALIZATIONS...");
			chartConfigCurrentHospitalizationsByTime = chartConfigStore
					.produceChartConfigFromList(ChartTypes.HOSP_NOW_VERSUS_TIME, chartDataCurrentHospitalizationsByTime, region);
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

	@Override
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats) {
		/////// CALLED FROM DashboardConfigServiceImpl ///////
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY U.S. TOTALS");
		log.debug("Getting U.S. data for populating By U.S. Totals row of dashboard...");
		List<UnitedStatesData> usaData = RegionsData.USA.getCoronaVirusDataFromExternalSource(dataFactory.getExternalDataService(RegionsData.USA.name()));
		int totalUSCases = usaData.get(usaData.size() - 1).getTotalPositiveCases();
		log.debug("Making totalUSCases");
		dashStats.setTotalUsCases(totalUSCases);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionCasesToUsCases(dashStats.getCasesTotal() * 100.0 / totalUSCases);
		int totalUSDeaths = usaData.get(usaData.size() - 1).getTotalDeaths();
		log.debug("Making totalUSDeaths");
		dashStats.setTotalUsDeaths(totalUSDeaths);
		log.debug("Making proportionOfRegionDeathsToUsCases");
		dashStats.setProportionOfRegionDeathsToUsDeaths(dashStats.getDeathsTotal() * 100.0 / totalUSDeaths);
		log.debug("Making proportionOfRegionPopToUsPop");
		dashStats.setProportionOfRegionPopToUsPop(regionPopulation * 100.0 / RegionsData.USA.getRegionData().getPopulation());
		int totalUSVacc = usaData.get(usaData.size() - 1).getTotalVaccCompleted();
		log.debug("Making totalUSVacc");
		dashStats.setTotalUsVacc(totalUSVacc);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionVaccToUsVacc(dashStats.getTotalVaccCompleted() * 100.0 / totalUSVacc);
	}

	////// CALLED FROM ABOVE //////
	@Override
	public void makeDashboardStatsForRegion(DashboardStatistics dashStats, int regionPopulation,
			List<List<Map<Object, Object>>> chartDataCasesByTime,
			List<List<Map<Object, Object>>> chartDataCasesMovingSum,
			List<List<Map<Object, Object>>> chartDataDeathsByTime,
			List<List<Map<Object, Object>>> chartDataVaccByTime) {
		log.info("Making all the GENERAL DASHBOARD STATISTICS FOR REGION");
		///////// CASES /////////
		dashStats.setCasesTotal((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setCasesToday((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y")
				- (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 2).get("y"));
		dashStats.setCasesMovingSumPrimary((double) chartDataCasesMovingSum.get(0).get(chartDataCasesMovingSum.get(0).size() - 1).get("y"));
		dashStats.setCasesMovingSumSecondary((double) chartDataCasesMovingSum.get(1).get(chartDataCasesMovingSum.get(1).size() - 1).get("y"));
		
		///////// DEATHS /////////
		dashStats.setDeathsTotal((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y"));
		dashStats.setDeathsToday((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y")
				- (int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 2).get("y"));
		dashStats.setDeathsMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataDeathsByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		dashStats.setDeathsMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataDeathsByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		
		///////// VACCINATIONS /////////
		dashStats.setTotalVaccCompleted((int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 1).get("y"));
		dashStats.setVaccToday((int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 1).get("y")
				- (int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 2).get("y"));
		dashStats.setVaccMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataVaccByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		dashStats.setVaccMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataVaccByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
	}

	@Override
	////// CALLED FROM ABOVE //////
	public <T extends CanonicalCaseDeathData> void makeDashboardStatsForUSRegionsByTesting(List<T> dataList, DashboardStatistics dashStats) {
		log.debug("Getting the region population from the Regions enum");
		int usaPop = RegionsData.USA.getRegionData().getPopulation();
//		log.debug("Making total tests conducted");
//		dashStats.setTotalTestsConducted(dataList.get(dataList.size() - 1).getTotalPositiveCases()
//				+ dataList.get(dataList.size() - 1).getTotalNegativeCases());
//		log.debug("Making ProportionOfPositiveTests and ProportionOfPositiveTestsMovingAverage");
//		dashStats.setProportionOfPositiveTests(dataList.get(dataList.size() - 1).getTotalPositiveCases()
//				* 100.0 / dashStats.getTotalTestsConducted());
//		int totalTestsLastN = computeTotalTestsLastN(dataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue());
//		dashStats.setTotalTestsConductedLastN(totalTestsLastN);
//		dashStats.setProportionOfPositiveTestsMovingAverage(
//				computeTotalPositivesLastN(dataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())* 100.0 / totalTestsLastN);
//		log.debug("Making ProportionOfPopulationTested");
//		dashStats.setProportionOfPopulationTested(dashStats.getTotalTestsConducted()
//				* 100.0 / usaPop);
		log.debug("Making ProportionOfDeathsFromPositives");
		dashStats.setProportionOfDeathsFromPositives(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dataList.get(dataList.size() - 1).getTotalPositiveCases());
//		log.debug("Making ProportionOfDeathsFromTested");
//		dashStats.setProportionOfDeathsFromTested(dataList.get(dataList.size() - 1).getTotalDeaths()
//				* 100.0 / dashStats.getTotalTestsConducted());
//		log.debug("Making ProportionOfDeathsOfExtrapolatedCases");
//		dashStats.setProportionOfDeathsOfExtrapolatedCases(dataList.get(dataList.size() - 1).getTotalDeaths()
//				* 100.0 / (dashStats.getProportionOfPositiveTests() * usaPop));
		log.debug("Making ProportionOfPopulationVaccinated");
		dashStats.setProportionOfPopulationVaccinated(dashStats.getTotalVaccCompleted()
				* 100.0 / usaPop);
	}
}
