package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.charts.ChartCsvHeaders;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.charts.ChartService;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.dashboard.DashboardChart;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;
import com.royware.corona.dashboard.model.dashboard.DashboardChartData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

@Component
public class DashboardChartServiceImpl implements DashboardChartService {
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardChartServiceImpl.class);
	
	@Override
	public <T extends CanonicalData> List<DashboardChart> makeAllDashboardCharts(
			List<T> dataList, String region, Integer regionPopulation, DashboardStatistics dashStats) {
		
		List<DashboardChart> dashboardList = new ArrayList<>();
		boolean isNotWorld = true;
		if(region.length() == 3 && !region.equalsIgnoreCase("USA")) {
			isNotWorld = false;
		}
		
		////////// CHART DATA LISTS - CASES //////////
		log.info("Making all the chart data lists for CASES");
		List<List<Map<Object, Object>>> chartDataCasesByTime = chartService.getTotalCasesVersusTimeWithExponentialFit(dataList);
		List<List<Map<Object, Object>>> chartDataRateOfCasesByTime = chartService.getDailyRateOfChangeOfCasesWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime = chartService.getDailyAccelerationOfCasesWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases = chartService.getChangeInTotalCasesVersusCaseswithExponentialLine(dataList);
		List<List<Map<Object, Object>>> chartDataTotalCurrentCases = chartService.getCurrentTotalPositivesWithPercentOfPopulation(dataList, regionPopulation);
		
		////////// CHART DATA LISTS - DEATHS /////////
		log.info("Making all the chart data lists for DEATHS");
		List<List<Map<Object, Object>>> chartDataDeathsByTime = chartService.getTotalDeathsVersusTimeWithExponentialFit(dataList);
		List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime = chartService.getDailyRateOfChangeOfDeathsWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime = chartService.getDailyAccelerationOfDeathsWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataChangeOfDeathsByDeaths = chartService.getChangeInTotalDeathsVersusDeathsWithExponentialLine(dataList);

		////////// CHART DATA LISTS - TESTS /////////
		List<List<Map<Object, Object>>> chartDataTestsByTime = null;
		List<List<Map<Object, Object>>> chartDataRatioOfCasesToTestsByTime = null;
		if(isNotWorld) {
			log.info("Making all the chart data lists for TESTS");
			chartDataTestsByTime = chartService.getDailyTestsTotalTestsVersusTime(dataList);
			chartDataRatioOfCasesToTestsByTime = chartService.getDailyRatioCasesToTestsWithMovingAverage(dataList);
		}

		////////// CHART DATA LISTS - HOSPITALIZATIONS /////////
		List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime = null;
		List<List<Map<Object, Object>>> chartDataCumulativeHospitalizationsByTime = null;
		if(isNotWorld) {
			log.info("Making all the chart data lists for HOSPITALIZATIONS");
			chartDataCurrentHospitalizationsByTime = chartService.getDailyHospitalizedNowWithMovingAverage(dataList);
			chartDataCumulativeHospitalizationsByTime = chartService.getDailyHospitalizedTotalWithMovingAverage(dataList);
		}

		////////// DASHBOARD TABLE STATISTICS ///////////
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - ORIGINAL");
		makeDashboardStatsForRegion(dashStats, chartDataCasesByTime, chartDataRateOfCasesByTime,
				chartDataAccelOfCasesByTime, chartDataDeathsByTime, chartDataRateOfDeathsByTime,
				chartDataAccelOfDeathsByTime);
		
		if(isNotWorld) {
			log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY TESTING");
			makeDashboardStatsForUSRegionsByTesting(dataList, dashStats);
		}
		
		////////// CHART CONFIGURATION - CASES ///////////
		log.info("Configuring all the charts for CASES...");
		DashboardChartConfig chartConfigCasesByTime = chartConfigCasesByTime(region, chartDataCasesByTime);
		DashboardChartConfig chartConfigRateOfChangeOfCases = chartConfigRateOfChangeOfCases(region, chartDataRateOfCasesByTime);
//		DashboardChartConfig chartConfigAccelerationOfCases = chartConfigAccelerationOfCases(region, chartDataAccelOfCasesByTime);
		DashboardChartConfig chartConfigRateOfCasesVersusCases = chartConfigRateOfCasesVersusCases(region, chartDataChangeOfCasesByCases);
		DashboardChartConfig chartConfigTotalCurrentCases = chartConfigTotalCurrentCases(region, chartDataTotalCurrentCases);
		
		////////// CHART CONFIGURATION - DEATHS ///////////
		log.info("Configuring all the charts for DEATHS...");
		DashboardChartConfig chartConfigDeathsByTime = chartConfigDeathsByTime(region, chartDataDeathsByTime);
		DashboardChartConfig chartConfigRateOfChangeOfDeaths = chartConfigRateOfChangeOfDeaths(region, chartDataRateOfDeathsByTime);
		DashboardChartConfig chartConfigAccelerationOfDeaths = chartConfigAccelerationOfDeaths(region, chartDataAccelOfDeathsByTime);
		DashboardChartConfig chartConfigRateOfDeathsVersusDeaths = chartConfigRateOfDeathsVersusDeaths(region, chartDataChangeOfDeathsByDeaths);
		
		////////// CHART CONFIGURATION - TESTS ///////////
		DashboardChartConfig chartConfigTestsByTime = null;
		DashboardChartConfig chartConfigRatioOfCasesToTestsByTime = null;
		if(isNotWorld) {
			log.info("Configuring all the charts for TESTS...");
			chartConfigTestsByTime = chartConfigTestsByTime(region, chartDataTestsByTime);
			chartConfigRatioOfCasesToTestsByTime = chartConfigRatioOfCasesToTestsByTime(region, chartDataRatioOfCasesToTestsByTime);
		}
		
		////////// CHART CONFIGURATION - TESTS ///////////
		DashboardChartConfig chartConfigCurrentHospitalizationsByTime = null;
		DashboardChartConfig chartConfigCumulativeHospitalizationsByTime = null;
		if(isNotWorld) {
			log.info("Configuring all the charts for HOSPITALIZATIONS...");
			chartConfigCurrentHospitalizationsByTime = chartConfigCurrentHospitalizationsByTime(region, chartDataCurrentHospitalizationsByTime);
			chartConfigCumulativeHospitalizationsByTime = chartConfigCumulativeHospitalizationsByTime(region, chartDataCumulativeHospitalizationsByTime);
		}
		
		//////// WRITE TO DASHBOARD CONFIGURATION LIST ////////
		log.info("Writing all the configurations to the dashboardList...");
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
					.withChartDataLists(chartDataRateOfCasesByTime)
					.withCsvHeader(ChartCsvHeaders.CASES_RATE.getName())
					.build())
				.setChartConfig(chartConfigRateOfChangeOfCases)
				.setRegion(region)
				.build());
//		dashboardList.add(new DashboardChart.Builder()
//				.setChartData(
//					new DashboardChartData.Builder()
//					.withChartDataLists(chartDataAccelOfCasesByTime)
//					.withCsvHeader(ChartCsvHeaders.CASES_ACCEL.getName())
//					.build())
//				.setChartConfig(chartConfigAccelerationOfCases)
//				.setRegion(region)
//				.build());
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
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataRateOfDeathsByTime)
					.withCsvHeader(ChartCsvHeaders.DEATHS_RATE.getName())
					.build())
				.setChartConfig(chartConfigRateOfChangeOfDeaths)
				.setRegion(region)
				.build());
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataAccelOfDeathsByTime)
					.withCsvHeader(ChartCsvHeaders.DEATHS_ACCEL.getName())
					.build())
				.setChartConfig(chartConfigAccelerationOfDeaths)
				.setRegion(region)
				.build());
		dashboardList.add(new DashboardChart.Builder()
				.setChartData(
					new DashboardChartData.Builder()
					.withChartDataLists(chartDataChangeOfDeathsByDeaths)
					.withCsvHeader(ChartCsvHeaders.DEATHS_CHG_BY_CASES.getName())
					.build())
				.setChartConfig(chartConfigRateOfDeathsVersusDeaths)
				.setRegion(region)
				.build());
		
		if(isNotWorld) {
			///// TESTS /////
			dashboardList.add(new DashboardChart.Builder()
					.setChartData(
						new DashboardChartData.Builder()
						.withChartDataLists(chartDataTestsByTime)
						.withCsvHeader(ChartCsvHeaders.TESTS_TIME_SERIES.getName())
						.build())
					.setChartConfig(chartConfigTestsByTime)
					.setRegion(region)
					.build());
			dashboardList.add(new DashboardChart.Builder()
					.setChartData(
						new DashboardChartData.Builder()
						.withChartDataLists(chartDataRatioOfCasesToTestsByTime)
						.withCsvHeader(ChartCsvHeaders.TESTS_RATIO.getName())
						.build())
					.setChartConfig(chartConfigRatioOfCasesToTestsByTime)
					.setRegion(region)
					.build());

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
			dashboardList.add(new DashboardChart.Builder()
					.setChartData(
						new DashboardChartData.Builder()
						.withChartDataLists(chartDataCumulativeHospitalizationsByTime)
						.withCsvHeader(ChartCsvHeaders.HOSP_CUMULATIVE.getName())
						.build())
					.setChartConfig(chartConfigCumulativeHospitalizationsByTime)
					.setRegion(region)
					.build());
		}

		return dashboardList;
	}

	@Override
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats) {
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY U.S. TOTALS");
		log.info("Getting U.S. data for populating By U.S. Totals row of dashboard...");
		List<UnitedStatesData> usaData = RegionsData.USA.getCoronaVirusDataFromExternalSource(dataFactory.getExternalDataService(RegionsData.USA.name()));
		int totalUsCases = usaData.get(usaData.size() - 1).getTotalPositiveCases();
		log.info("Making totalUsCases");
		dashStats.setTotalUsCases(totalUsCases);
		log.info("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionCasesToUsCases(dashStats.getCasesTotal() * 100.0 / totalUsCases);
		int totalUsDeaths = usaData.get(usaData.size() - 1).getTotalDeaths();
		log.info("Making totalUsDeaths");
		dashStats.setTotalUsDeaths(totalUsDeaths);
		log.info("Making proportionOfRegionDeathsToUsCases");
		dashStats.setProportionOfRegionDeathsToUsDeaths(dashStats.getDeathsTotal() * 100.0 / totalUsDeaths);
		log.info("Making proportionOfRegionPopToUsPop");
		dashStats.setProportionOfRegionPopToUsPop(regionPopulation * 100.0 / RegionsData.USA.getRegionData().getPopulation());
	}

	@Override
	public void makeDashboardStatsForRegion(DashboardStatistics dashStats,
			List<List<Map<Object, Object>>> chartDataCasesByTime,
			List<List<Map<Object, Object>>> chartDataRateOfCasesByTime,
			List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime,
			List<List<Map<Object, Object>>> chartDataDeathsByTime,
			List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime,
			List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime) {
		
		dashStats.setCasesTotal((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setCasesToday((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y")
				- (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 2).get("y"));
		dashStats.setRateOfCasesToday(
				(double) chartDataRateOfCasesByTime.get(0).get(chartDataRateOfCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setAccelOfCasesToday(
				(double) chartDataAccelOfCasesByTime.get(0).get(chartDataAccelOfCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setDeathsTotal((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y"));
		dashStats.setDeathsToday((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y")
				- (int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 2).get("y"));
		dashStats.setRateOfDeathsToday(
				(double) chartDataRateOfDeathsByTime.get(0).get(chartDataRateOfDeathsByTime.get(0).size() - 1).get("y"));
		dashStats.setAccelOfDeathsToday(
				(double) chartDataAccelOfDeathsByTime.get(0).get(chartDataAccelOfDeathsByTime.get(0).size() - 1).get("y"));
	}

	@Override
	public <T extends CanonicalData> void makeDashboardStatsForUSRegionsByTesting(List<T> dataList, DashboardStatistics dashStats) {
		log.info("Getting the region population from the Regions enum");
		int usaPop = RegionsData.USA.getRegionData().getPopulation();
		log.info("Making total tests conducted");
		dashStats.setTotalTestsConducted(dataList.get(dataList.size() - 1).getTotalPositiveCases()
				+ dataList.get(dataList.size() - 1).getTotalNegativeCases());
		log.info("Making ProportionOfPositiveTests");
		dashStats.setProportionOfPositiveTests(dataList.get(dataList.size() - 1).getTotalPositiveCases()
				* 100.0 / dashStats.getTotalTestsConducted());
		log.info("Making ProportionOfPopulationTested");
		dashStats.setProportionOfPopulationTested(dashStats.getTotalTestsConducted()
				* 100.0 / usaPop);
		log.info("Making ProportionOfDeathsFromPositives");
		dashStats.setProportionOfDeathsFromPositives(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dataList.get(dataList.size() - 1).getTotalPositiveCases());
		log.info("Making ProportionOfDeathsFromTested");
		dashStats.setProportionOfDeathsFromTested(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dashStats.getTotalTestsConducted());
		log.info("Making ProportionOfDeathsOfExtrapolatedCases");
		dashStats.setProportionOfDeathsOfExtrapolatedCases(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / (dashStats.getProportionOfPositiveTests() * usaPop));
	}

	@Override
	public DashboardChartConfig chartConfigCasesByTime(String region, List<List<Map<Object, Object>>> chartDataCasesByTime) {
		DashboardChartConfig chartConfig = new DashboardChartConfig("Time History of Cases " + region,
				"Days Since Cases > 0", "Total Cases", "scatter");
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		int maxX = (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		int maxY = getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(0));
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		chartConfig.setDataSeries1Name("Total Cases");
		chartConfig.setDataSeries2Name("4-day Moving Average of New Cases");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfChangeOfCases(String region, List<List<Map<Object, Object>>> chartDataRateOfCasesByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig(
				"Rate of Change of Cases in " + region, "Days Since Cases > 0", "Percent Change in New Cases",
				"scatter");
		chartConfig.setyAxisNumberSuffix("%");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataRateOfCasesByTime.get(0).get(chartDataRateOfCasesByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataRateOfCasesByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		chartConfig.setDataSeries1Name("% Change in Cases");
		chartConfig.setDataSeries2Name("4-day Moving Average");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigAccelerationOfCases(String region, List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfigAccelerationOfCases = new DashboardChartConfig(
				"Acceleration of Cases in " + region, "Days Since Cases > 0", "Percent Change in the Rate of New Cases",
				"scatter");
		chartConfigAccelerationOfCases.setyAxisNumberSuffix("%");
		chartConfigAccelerationOfCases.setxAxisPosition("bottom");
		chartConfigAccelerationOfCases.setxAxisLogarithmic("false");
		chartConfigAccelerationOfCases.setyAxisPosition("left");
		chartConfigAccelerationOfCases.setyAxisLogarithmic("false");
		chartConfigAccelerationOfCases.setShowLegend("true");
		chartConfigAccelerationOfCases.setDataPointSize(1);
		chartConfigAccelerationOfCases.setxGridDashType("dot");
		chartConfigAccelerationOfCases.setxAxisMin(0);
		maxX = (int) chartDataAccelOfCasesByTime.get(0).get(chartDataAccelOfCasesByTime.get(0).size() - 1).get("x");
		chartConfigAccelerationOfCases.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		int minY = getMinValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(0));
		int minYMovAvg = getMinValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(1));
		minY = (minY < minYMovAvg) ? minY : minYMovAvg;
		maxY = getMaxValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		minY = minY <= -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigAccelerationOfCases.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigAccelerationOfCases.setyAxisInterval(factor);
		chartConfigAccelerationOfCases.setyAxisMax(maxY / factor * factor + factor);

		chartConfigAccelerationOfCases.setLegendHorizonalAlign("right");
		chartConfigAccelerationOfCases.setLegendVerticalAlign("top");
		chartConfigAccelerationOfCases.setDataSeries1Name("Acceleration of Cases");
		chartConfigAccelerationOfCases.setDataSeries2Name("4-day Moving Average");
		return chartConfigAccelerationOfCases;
	}

	@Override
	public DashboardChartConfig chartConfigTotalCurrentCases(String region, List<List<Map<Object, Object>>> chartDataCasesByTime) {
		DashboardChartConfig chartConfig = new DashboardChartConfig("Time History of Current cases " + region,
				"Days Since Cases > 0", "Total Current Cases", "scatter");
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		int maxX = (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		int maxY = getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(0));
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		chartConfig.setDataSeries1Name("Total Current Cases");
		chartConfig.setDataSeries2Name("4-day Moving Average of Current Cases");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfCasesVersusCases(String region, List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases) {
		DashboardChartConfig chartConfigRateOfCasesVersusCases = new DashboardChartConfig(
				"Detecting Inflection of Cases in " + region, "Total Cases", "Daily Change in Total Cases", "scatter");
		chartConfigRateOfCasesVersusCases.setyAxisNumberSuffix("");
		chartConfigRateOfCasesVersusCases.setxAxisPosition("bottom");
		chartConfigRateOfCasesVersusCases.setxAxisLogarithmic("true");
		chartConfigRateOfCasesVersusCases.setyAxisPosition("left");
		chartConfigRateOfCasesVersusCases.setyAxisLogarithmic("true");
		chartConfigRateOfCasesVersusCases.setShowLegend("true");
		chartConfigRateOfCasesVersusCases.setDataPointSize(1);
		chartConfigRateOfCasesVersusCases.setxGridDashType("dot");
		int cases = (Integer) chartDataChangeOfCasesByCases.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		int exp = (int) Math.log10(cases);
		chartConfigRateOfCasesVersusCases.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartDataChangeOfCasesByCases.get(0).get(chartDataChangeOfCasesByCases.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfigRateOfCasesVersusCases.setxAxisMax((int) Math.pow(10, 1 + (int) Math.log10(cases)));
		Double minValue = (Double) chartDataChangeOfCasesByCases.get(0).get(0).get("y");
		int digits = minValue > 0 ? (int) Math.log10((Double) chartDataChangeOfCasesByCases.get(0).get(0).get("y")) : 1;
		chartConfigRateOfCasesVersusCases.setyAxisMin((int) Math.pow(10, digits));
		chartConfigRateOfCasesVersusCases.setyAxisMax((int) Math.pow(10,
				1 + (int) Math.log10((double) getMaxValueFromListOfXYMaps(chartDataChangeOfCasesByCases.get(1)))));

		chartConfigRateOfCasesVersusCases.setLegendHorizonalAlign("left");
		chartConfigRateOfCasesVersusCases.setLegendVerticalAlign("top");
		chartConfigRateOfCasesVersusCases.setDataSeries1Name("Daily change in cases");
		chartConfigRateOfCasesVersusCases.setDataSeries2Name("Pure exponential (k = 0.3)");
		return chartConfigRateOfCasesVersusCases;
	}

	@Override
	public DashboardChartConfig chartConfigDeathsByTime(String region, List<List<Map<Object, Object>>> chartDataDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfigDeathsByTime = new DashboardChartConfig("Time History of Deaths in " + region,
				"Days Since Deaths > 0", "Total Deaths", "scatter");
		chartConfigDeathsByTime.setyAxisNumberSuffix("");
		chartConfigDeathsByTime.setxAxisPosition("bottom");
		chartConfigDeathsByTime.setxAxisLogarithmic("false");
		chartConfigDeathsByTime.setyAxisPosition("left");
		chartConfigDeathsByTime.setyAxisLogarithmic("false");
		chartConfigDeathsByTime.setShowLegend("true");
		chartConfigDeathsByTime.setDataPointSize(1);
		chartConfigDeathsByTime.setxGridDashType("dot");
		chartConfigDeathsByTime.setxAxisMin(0);
		maxX = (int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("x");
		chartConfigDeathsByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigDeathsByTime.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataDeathsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigDeathsByTime.setyAxisMax(maxY / factor * factor + factor);
		chartConfigDeathsByTime.setyAxisInterval(factor);

		chartConfigDeathsByTime.setLegendHorizonalAlign("left");
		chartConfigDeathsByTime.setLegendVerticalAlign("top");
		chartConfigDeathsByTime.setDataSeries1Name("Total Deaths");
		chartConfigDeathsByTime.setDataSeries2Name("4-day Moving Average of New Deaths");
		return chartConfigDeathsByTime;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfChangeOfDeaths(String region, List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfigRateOfChangeOfDeaths = new DashboardChartConfig(
				"Rate of Change of Deaths in " + region, "Days Since Deaths > 0", "Percent Change in New Deaths",
				"scatter");
		chartConfigRateOfChangeOfDeaths.setyAxisNumberSuffix("%");
		chartConfigRateOfChangeOfDeaths.setxAxisPosition("bottom");
		chartConfigRateOfChangeOfDeaths.setxAxisLogarithmic("false");
		chartConfigRateOfChangeOfDeaths.setyAxisPosition("left");
		chartConfigRateOfChangeOfDeaths.setyAxisLogarithmic("false");
		chartConfigRateOfChangeOfDeaths.setShowLegend("true");
		chartConfigRateOfChangeOfDeaths.setDataPointSize(1);
		chartConfigRateOfChangeOfDeaths.setxAxisGridlinesDisplay("dot");
		chartConfigRateOfChangeOfDeaths.setxAxisMin(0);
		maxX = (int) chartDataRateOfDeathsByTime.get(0).get(chartDataRateOfDeathsByTime.get(0).size() - 1).get("x");
		chartConfigRateOfChangeOfDeaths.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigRateOfChangeOfDeaths.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataRateOfDeathsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigRateOfChangeOfDeaths.setyAxisInterval(factor);
		chartConfigRateOfChangeOfDeaths.setyAxisMax(maxY / factor * factor + factor);

		chartConfigRateOfChangeOfDeaths.setLegendHorizonalAlign("right");
		chartConfigRateOfChangeOfDeaths.setLegendVerticalAlign("top");
		chartConfigRateOfChangeOfDeaths.setDataSeries1Name("% change in deaths");
		chartConfigRateOfChangeOfDeaths.setDataSeries2Name("4-day Moving Average");
		return chartConfigRateOfChangeOfDeaths;
	}

	@Override
	public DashboardChartConfig chartConfigAccelerationOfDeaths(String region, List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		int minY;
		DashboardChartConfig chartConfigAccelerationOfDeaths = new DashboardChartConfig(
				"Acceleration of Deaths in " + region, "Days Since Deaths > 0", "Percent Change in the Rate of New Deaths",
				"scatter");
		chartConfigAccelerationOfDeaths.setyAxisNumberSuffix("%");
		chartConfigAccelerationOfDeaths.setxAxisPosition("bottom");
		chartConfigAccelerationOfDeaths.setxAxisLogarithmic("false");
		chartConfigAccelerationOfDeaths.setyAxisPosition("left");
		chartConfigAccelerationOfDeaths.setyAxisLogarithmic("false");
		chartConfigAccelerationOfDeaths.setShowLegend("true");
		chartConfigAccelerationOfDeaths.setDataPointSize(1);
		chartConfigAccelerationOfDeaths.setxGridDashType("dot");
		chartConfigAccelerationOfDeaths.setxAxisMin(0);
		maxX = (int) chartDataAccelOfDeathsByTime.get(0).get(chartDataAccelOfDeathsByTime.get(0).size() - 1).get("x");
		chartConfigAccelerationOfDeaths.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		minY = getMinValueFromListOfXYMaps(chartDataAccelOfDeathsByTime.get(0));
		maxY = getMaxValueFromListOfXYMaps(chartDataAccelOfDeathsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		minY = minY <= -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigAccelerationOfDeaths.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigAccelerationOfDeaths.setyAxisInterval(factor);
		chartConfigAccelerationOfDeaths.setyAxisMax(maxY / factor * factor + factor);

		chartConfigAccelerationOfDeaths.setLegendHorizonalAlign("right");
		chartConfigAccelerationOfDeaths.setLegendVerticalAlign("top");
		chartConfigAccelerationOfDeaths.setDataSeries1Name("Acceleration of Deaths");
		chartConfigAccelerationOfDeaths.setDataSeries2Name("4-day Moving Average");
		return chartConfigAccelerationOfDeaths;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfDeathsVersusDeaths(String region, List<List<Map<Object, Object>>> chartDataChangeOfDeathsByDeaths) {
		int cases;
		int exp;
		Double minValue;
		DashboardChartConfig chartConfigRateOfDeathsVersusDeaths = new DashboardChartConfig(
				"Detecting Inflection of Deaths in " + region, "Total Deaths", "Daily Change in Total Deaths",
				"scatter");
		chartConfigRateOfDeathsVersusDeaths.setyAxisNumberSuffix("");
		chartConfigRateOfDeathsVersusDeaths.setxAxisPosition("bottom");
		chartConfigRateOfDeathsVersusDeaths.setxAxisLogarithmic("true");
		chartConfigRateOfDeathsVersusDeaths.setyAxisPosition("left");
		chartConfigRateOfDeathsVersusDeaths.setyAxisLogarithmic("true");
		chartConfigRateOfDeathsVersusDeaths.setShowLegend("true");
		chartConfigRateOfDeathsVersusDeaths.setDataPointSize(1);
		chartConfigRateOfDeathsVersusDeaths.setxGridDashType("dot");
		cases = (Integer) chartDataChangeOfDeathsByDeaths.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfigRateOfDeathsVersusDeaths.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartDataChangeOfDeathsByDeaths.get(0).get(chartDataChangeOfDeathsByDeaths.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfigRateOfDeathsVersusDeaths.setxAxisMax((int) Math.pow(10, 1 + exp));
		minValue = Double.valueOf(getMinValueFromListOfXYMaps(chartDataChangeOfDeathsByDeaths.get(0)));
		exp = minValue > 0 ? (int) Math.log10(minValue) : 0;
		chartConfigRateOfDeathsVersusDeaths.setyAxisMin((int) Math.pow(10, exp));
		double maxValue = (double) getMaxValueFromListOfXYMaps(chartDataChangeOfDeathsByDeaths.get(1));
		maxValue = maxValue > 0 ? maxValue : 1;
		chartConfigRateOfDeathsVersusDeaths.setyAxisMax((int) Math.pow(10, 1 + (int) Math.log10(maxValue)));

		chartConfigRateOfDeathsVersusDeaths.setLegendHorizonalAlign("left");
		chartConfigRateOfDeathsVersusDeaths.setLegendVerticalAlign("top");
		chartConfigRateOfDeathsVersusDeaths.setDataSeries1Name("Daily change in deaths");
		chartConfigRateOfDeathsVersusDeaths.setDataSeries2Name("Pure exponential (k = 0.3)");
		return chartConfigRateOfDeathsVersusDeaths;
	}

	@Override
	public DashboardChartConfig chartConfigRatioOfCasesToTestsByTime(String region, List<List<Map<Object, Object>>> chartDataRatioOfCasesToTestsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfigRatioOfCasesToTestsByTime;
		chartConfigRatioOfCasesToTestsByTime = new DashboardChartConfig(
				"Ratio of Positives to Tests in " + region, "Days Since Cases > 0", "Ratio of Positives to Tests",
				"scatter");
		chartConfigRatioOfCasesToTestsByTime.setyAxisNumberSuffix("%");
		chartConfigRatioOfCasesToTestsByTime.setxAxisPosition("bottom");
		chartConfigRatioOfCasesToTestsByTime.setxAxisLogarithmic("false");
		chartConfigRatioOfCasesToTestsByTime.setyAxisPosition("left");
		chartConfigRatioOfCasesToTestsByTime.setyAxisLogarithmic("false");
		chartConfigRatioOfCasesToTestsByTime.setShowLegend("true");
		chartConfigRatioOfCasesToTestsByTime.setDataPointSize(1);
		chartConfigRatioOfCasesToTestsByTime.setxGridDashType("dot");
		chartConfigRatioOfCasesToTestsByTime.setxAxisMin(0);
		maxX = (int) chartDataRatioOfCasesToTestsByTime.get(0).get(chartDataRatioOfCasesToTestsByTime.get(0).size() - 1).get("x");
		chartConfigRatioOfCasesToTestsByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigRatioOfCasesToTestsByTime.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataRatioOfCasesToTestsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigRatioOfCasesToTestsByTime.setyAxisMax(maxY / factor * factor + factor);
		chartConfigRatioOfCasesToTestsByTime.setyAxisInterval(factor);

		chartConfigRatioOfCasesToTestsByTime.setLegendHorizonalAlign("right");
		chartConfigRatioOfCasesToTestsByTime.setLegendVerticalAlign("top");
		chartConfigRatioOfCasesToTestsByTime.setDataSeries1Name("% Positive per Test");
		chartConfigRatioOfCasesToTestsByTime.setDataSeries2Name("4-day Moving Average");
		return chartConfigRatioOfCasesToTestsByTime;
	}

	@Override
	public DashboardChartConfig chartConfigTestsByTime(String region, List<List<Map<Object, Object>>> chartDataTestsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfigTestsByTime;
		chartConfigTestsByTime = new DashboardChartConfig("Time History of Tests " + region,
				"Days Since Tests > 0", "Total Tests", "scatter");
		chartConfigTestsByTime.setyAxisNumberSuffix("");
		chartConfigTestsByTime.setxAxisPosition("bottom");
		chartConfigTestsByTime.setxAxisLogarithmic("false");
		chartConfigTestsByTime.setyAxisPosition("left");
		chartConfigTestsByTime.setyAxisLogarithmic("false");
		chartConfigTestsByTime.setShowLegend("true");
		chartConfigTestsByTime.setDataPointSize(1);
		chartConfigTestsByTime.setxGridDashType("dot");
		chartConfigTestsByTime.setxAxisMin(0);
		maxX = (int) chartDataTestsByTime.get(0).get(chartDataTestsByTime.get(0).size() - 1).get("x");
		chartConfigTestsByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigTestsByTime.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataTestsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigTestsByTime.setyAxisMax(maxY / factor * factor + factor);
		chartConfigTestsByTime.setyAxisInterval(factor);

		chartConfigTestsByTime.setLegendHorizonalAlign("left");
		chartConfigTestsByTime.setLegendVerticalAlign("top");
		chartConfigTestsByTime.setDataSeries1Name("Total Tests");
		chartConfigTestsByTime.setDataSeries2Name("4-day Moving Average of New Tests");
		return chartConfigTestsByTime;
	}

	@Override
	public DashboardChartConfig chartConfigCurrentHospitalizationsByTime(String region,
			List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig("Time History of Current Hospitalizations in " + region,
				"Days Since Hospitalizations > 0", "Current Hospitalizations", "scatter");
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataCurrentHospitalizationsByTime.get(0).get(chartDataCurrentHospitalizationsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataCurrentHospitalizationsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		chartConfig.setDataSeries1Name("Current Hospitalizations");
		chartConfig.setDataSeries2Name("4-day Moving Average of New Hospitalizations");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigCumulativeHospitalizationsByTime(String region,
			List<List<Map<Object, Object>>> chartDataCumulativeHospitalizationsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig("Time History of Cumulative Hospitalizations in " + region,
				"Days Since Hospitalizations > 0", "Cumulative Hospitalizations", "scatter");
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataCumulativeHospitalizationsByTime.get(0).get(chartDataCumulativeHospitalizationsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataCumulativeHospitalizationsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		chartConfig.setDataSeries1Name("Cumulative Hospitalizations");
		chartConfig.setDataSeries2Name("4-day Moving Average of New Hospitalizations");
		return chartConfig;
	}

	////// HELPER METHODS /////////
	private int getMinValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		Double min = Double.valueOf(dataList.get(0).get("y").toString());

		for (Map<Object, Object> xy : dataList) {
			if (Double.valueOf(xy.get("y").toString()) < min) {
				min = Double.valueOf(xy.get("y").toString());
			}
		}
		if (min.isNaN() || min.isInfinite()) {
			min = 0.0;
		}
		return min.intValue();
	}

	private int getMaxValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		Double max = Double.valueOf(dataList.get(0).get("y").toString());

		for (Map<Object, Object> xy : dataList) {
			if (Double.valueOf(xy.get("y").toString()) > max) {
				max = Double.valueOf(xy.get("y").toString());
			}
		}
		if (max.isNaN() || max.isInfinite()) {
			max = 99.0;
		}
		return max.intValue();
	}
}
