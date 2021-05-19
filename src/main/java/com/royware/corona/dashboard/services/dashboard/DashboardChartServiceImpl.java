package com.royware.corona.dashboard.services.dashboard;

import java.text.NumberFormat;
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
		
		//TODO: Move all chartConfig methods into a simple factory pattern
		//TODO: Move makeDashboardRowByUsTotals into its own class
		//TODO: Move makeDashboardStats... methods into separate class(es)
		
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
		DashboardChartConfig chartConfigCasesByTime = chartConfigCasesByTime(region, chartDataCasesByTime);
		DashboardChartConfig chartConfigTotalCurrentCases = chartConfigTotalCurrentCases(region, chartDataTotalCurrentCases);
		DashboardChartConfig chartConfigRateOfCasesVersusCases = chartConfigRateOfCasesVersusCases(region, chartDataChangeOfCasesByCases);
		
		////////// CHART CONFIGURATION - DEATHS ///////////
		log.debug("Configuring all the charts for DEATHS...");
		DashboardChartConfig chartConfigDeathsByTime = chartConfigDeathsByTime(region, chartDataDeathsByTime);
		
		////////// CHART CONFIGURATION - VACCINATIONS ///////////
		DashboardChartConfig chartConfigVaccByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for VACCINATIONS...");
			chartConfigVaccByTime = chartConfigVaccByTime(region, chartDataVaccByTime);
		}
		
		////////// CHART CONFIGURATION - HOSPITALIZATIONS ///////////
		DashboardChartConfig chartConfigCurrentHospitalizationsByTime = null;
		if(isNotWorld) {
			log.debug("Configuring all the charts for HOSPITALIZATIONS...");
			chartConfigCurrentHospitalizationsByTime = chartConfigCurrentHospitalizationsByTime(region, chartDataCurrentHospitalizationsByTime);
		}
		
		//////// WRITE TO DASHBOARD CONFIGURATION LIST ////////
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

	//makeDashboardStatsForRegion(dashStats, chartDataCasesByTime, chartDataTotalCurrentCases, chartDataDeathsByTime, chartDataVaccByTime);
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
	
	@Override
	public DashboardChartConfig chartConfigCasesByTime(String region, List<List<Map<Object, Object>>> chartDataCasesByTime) {
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartTitle("Time History of Positives in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Total Positive Tests");
		chartConfig.setDataSeries1Name("Total Positives");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Positives");

		chartConfig.setChartType("scatter");
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
		int maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(0));
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfChangeOfCases(String region, List<List<Map<Object, Object>>> chartDataRateOfCasesByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartTitle("Rate of Change of Positives in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Percent Change in New Positives");
		chartConfig.setDataSeries1Name("% Change in Positives");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");

		chartConfig.setChartType("scatter");
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
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataRateOfCasesByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigAccelerationOfCases(String region, List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartTitle("Acceleration of Positives in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Percent Change in Rate of New Positives");
		chartConfig.setDataSeries1Name("Acceleration of Positives");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");

		chartConfig.setChartType("scatter");
		chartConfig.setyAxisNumberSuffix("%");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataAccelOfCasesByTime.get(0).get(chartDataAccelOfCasesByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		int minY = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(0));
		int minYMovAvg = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(1));
		minY = (minY < minYMovAvg) ? minY : minYMovAvg;
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataAccelOfCasesByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		minY = minY <= -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisInterval(factor);
		chartConfig.setyAxisMax(maxY / factor * factor + factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigTotalCurrentCases(String region, List<List<Map<Object, Object>>> chartDataCasesByTime) {
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Positivity Rate in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Positives per " + NumberFormat.getNumberInstance().format(MovingAverageSizes.PER_CAPITA_BASIS.getValue()));
		chartConfig.setDataSeries1Name("Positives per " + NumberFormat.getNumberInstance().format(MovingAverageSizes.PER_CAPITA_BASIS.getValue())
				+ " (Last " + MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue() + " days)");
		chartConfig.setDataSeries2Name("Positives per " + NumberFormat.getNumberInstance().format(MovingAverageSizes.PER_CAPITA_BASIS.getValue())
				+ " (Last " + MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue() + " days)");

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
		int maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(0));
		int maxY2 = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(1));
		maxY = maxY > maxY2 ? maxY : maxY2;
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfCasesVersusCases(String region, List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases) {
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Detecting Inflection of Positives in " + region);
		chartConfig.setxAxisTitle("Total Positive Cases");
		chartConfig.setyAxisTitle("Daily Change in Total Positive Cases");
		chartConfig.setDataSeries1Name("Daily change in positives");
		chartConfig.setDataSeries2Name("Pure exponential (k = 0.3)");
		
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("true");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("true");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		int cases = (Integer) chartDataChangeOfCasesByCases.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		int exp = (int) Math.log10(cases);
		chartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartDataChangeOfCasesByCases.get(0).get(chartDataChangeOfCasesByCases.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMax((int) Math.pow(10, 1 + (int) Math.log10(cases)));
		Double minValue = (Double) chartDataChangeOfCasesByCases.get(0).get(0).get("y");
		int digits = minValue > 0 ? (int) Math.log10((Double) chartDataChangeOfCasesByCases.get(0).get(0).get("y")) : 1;
		chartConfig.setyAxisMin((int) Math.pow(10, digits));
		chartConfig.setyAxisMax((int) Math.pow(10,
				1 + (int) Math.log10((double) chartDataChangeOfCasesByCases.get(1).get(1).get("y"))));

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigDeathsByTime(String region, List<List<Map<Object, Object>>> chartDataDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Deaths in " + region);
		chartConfig.setxAxisTitle("Days Since Deaths > 0");
		chartConfig.setyAxisTitle("Total Deaths");
		chartConfig.setDataSeries1Name("Total Deaths");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Deaths");
		
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataDeathsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfChangeOfDeaths(String region, List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Rate of Change of Deaths in " + region);
		chartConfig.setxAxisTitle("Days Since Deaths > 0");
		chartConfig.setyAxisTitle("Percent Change in New Deaths");
		chartConfig.setDataSeries1Name("% change in deaths");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");
		
		chartConfig.setyAxisNumberSuffix("%");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxAxisGridlinesDisplay("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataRateOfDeathsByTime.get(0).get(chartDataRateOfDeathsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataRateOfDeathsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisInterval(factor);
		chartConfig.setyAxisMax(maxY / factor * factor + factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigAccelerationOfDeaths(String region, List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime) {
		int maxX;
		int maxY;
		int factor;
		int minY;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Acceleration of Deaths in " + region);
		chartConfig.setxAxisTitle("Days Since Deaths > 0");
		chartConfig.setyAxisTitle("Percent Change in Rate of New Deaths");
		chartConfig.setDataSeries1Name("Acceleration of Deaths");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");
		
		chartConfig.setyAxisNumberSuffix("%");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataAccelOfDeathsByTime.get(0).get(chartDataAccelOfDeathsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		minY = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartDataAccelOfDeathsByTime.get(0));
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataAccelOfDeathsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		minY = minY <= -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisInterval(factor);
		chartConfig.setyAxisMax(maxY / factor * factor + factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRateOfDeathsVersusDeaths(String region, List<List<Map<Object, Object>>> chartDataChangeOfDeathsByDeaths) {
		int cases;
		int exp;
		Double minValue;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Detecting Inflection of Deaths in " + region);
		chartConfig.setxAxisTitle("Total Deaths");
		chartConfig.setyAxisTitle("Daily Change in Total Deaths");
		chartConfig.setDataSeries1Name("Daily change in deaths");
		chartConfig.setDataSeries2Name("Pure exponential (k = 0.3)");
		
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("true");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("true");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		cases = (Integer) chartDataChangeOfDeathsByDeaths.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartDataChangeOfDeathsByDeaths.get(0).get(chartDataChangeOfDeathsByDeaths.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMax((int) Math.pow(10, 1 + exp));
		minValue = Double.valueOf(ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartDataChangeOfDeathsByDeaths.get(0)));
		exp = minValue > 0 ? (int) Math.log10(minValue) : 0;
		chartConfig.setyAxisMin((int) Math.pow(10, exp));
		double maxValue = (double) chartDataChangeOfDeathsByDeaths.get(1).get(1).get("y");
		maxValue = maxValue > 0 ? maxValue : 1;
		chartConfig.setyAxisMax((int) Math.pow(10, 1 + (int) Math.log10(maxValue)));

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigRatioOfCasesToTestsByTime(String region, List<List<Map<Object, Object>>> chartDataRatioOfCasesToTestsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Positivity Rate in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Ratio of Positives to Tests, %");
		chartConfig.setDataSeries1Name("% Positive per Test");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");
		
		chartConfig.setyAxisNumberSuffix("%");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataRatioOfCasesToTestsByTime.get(0).get(chartDataRatioOfCasesToTestsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataRatioOfCasesToTestsByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("right");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigTestsByTime(String region, List<List<Map<Object, Object>>> chartDataTestsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Tests in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Total Tests");
		chartConfig.setDataSeries1Name("Total Tests");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Tests");
		
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataTestsByTime.get(0).get(chartDataTestsByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataTestsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigVaccByTime(String region, List<List<Map<Object, Object>>> chartDataVaccByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Vaccinations in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Total Vaccinations");
		chartConfig.setDataSeries1Name("Total Vaccinations Completed");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Vacc");
		
		chartConfig.setyAxisNumberSuffix("");
		chartConfig.setxAxisPosition("bottom");
		chartConfig.setxAxisLogarithmic("false");
		chartConfig.setyAxisPosition("left");
		chartConfig.setyAxisLogarithmic("false");
		chartConfig.setShowLegend("true");
		chartConfig.setDataPointSize(1);
		chartConfig.setxGridDashType("dot");
		chartConfig.setxAxisMin(0);
		maxX = (int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataVaccByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	public DashboardChartConfig chartConfigCurrentHospitalizationsByTime(String region,
			List<List<Map<Object, Object>>> chartDataCurrentHospitalizationsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Current Hospitalizations in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Current Hospitalizations");
		chartConfig.setDataSeries1Name("Current Hospitalizations");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Hospitalizations");
		
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
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataCurrentHospitalizationsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}

	@Override
	public DashboardChartConfig chartConfigCumulativeHospitalizationsByTime(String region,
			List<List<Map<Object, Object>>> chartDataCumulativeHospitalizationsByTime) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartType("scatter");

		chartConfig.setChartTitle("Time History of Cumulative Hospitalizations in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Cumulative Hospitalizations");
		chartConfig.setDataSeries1Name("Cumulative Hospitalizations");
		chartConfig.setDataSeries2Name(MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average of New Hospitalizations");
		
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
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartDataCumulativeHospitalizationsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfig.setyAxisMax(maxY / factor * factor + factor);
		chartConfig.setyAxisInterval(factor);

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}
}
