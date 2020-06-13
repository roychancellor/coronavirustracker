package com.royware.corona.dashboard.services.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.regions.Regions;
import com.royware.corona.dashboard.interfaces.charts.ChartService;
import com.royware.corona.dashboard.interfaces.dashboard.DashboardChartService;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.dashboard.Dashboard;
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
	public <T extends CanonicalData> List<Dashboard> makeAllDashboardCharts(
			List<T> dataList, String region, Integer regionPopulation, DashboardStatistics dashStats) {
		List<Dashboard> dashboardList = new ArrayList<>();
		
		////////// CHART DATA LISTS - CASES //////////
		log.info("Making all the chart data lists for CASES");
		List<List<Map<Object, Object>>> chartDataCasesByTime = chartService
				.getTotalCasesVersusTimeWithExponentialFit(dataList);
		List<List<Map<Object, Object>>> chartDataRateOfCasesByTime = chartService
				.getDailyRateOfChangeOfCasesWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataAccelOfCasesByTime = chartService
				.getDailyAccelerationOfCasesWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataChangeOfCasesByCases = chartService
				.getChangeInTotalCasesVersusCaseswithExponentialLine(dataList);
		
		////////// CHART DATA LISTS - DEATHS /////////
		log.info("Making all the chart data lists for DEATHS");
		List<List<Map<Object, Object>>> chartDataDeathsByTime = chartService
				.getTotalDeathsVersusTimeWithExponentialFit(dataList);
		List<List<Map<Object, Object>>> chartDataRateOfDeathsByTime = chartService
				.getDailyRateOfChangeOfDeathsWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataAccelOfDeathsByTime = chartService
				.getDailyAccelerationOfDeathsWithMovingAverage(dataList);
		List<List<Map<Object, Object>>> chartDataChangeOfDeathsByDeaths = chartService
				.getChangeInTotalDeathsVersusDeathsWithExponentialLine(dataList);

		////////// DASHBOARD TABLE STATISTICS ///////////
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - ORIGINAL");
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
		
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY TESTING");
		log.info("Getting the region population from the Regions enum");
		int usaPop = Regions.USA.getRegionData().getPopulation();
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
		
		////////// CHART CONFIGURATION - CASES ///////////
		log.info("Configuring all the charts for CASES...");
		DashboardChartConfig chartConfigCasesByTime = new DashboardChartConfig("Time History of Cases " + region,
				"Days Since Cases > 0", "Total Cases", "scatter");
		chartConfigCasesByTime.setyAxisNumberSuffix("");
		chartConfigCasesByTime.setxAxisPosition("bottom");
		chartConfigCasesByTime.setxAxisLogarithmic("false");
		chartConfigCasesByTime.setyAxisPosition("left");
		chartConfigCasesByTime.setyAxisLogarithmic("false");
		chartConfigCasesByTime.setShowLegend("true");
		chartConfigCasesByTime.setDataPointSize(1);
		chartConfigCasesByTime.setxGridDashType("dot");
		chartConfigCasesByTime.setxAxisMin(0);
		int maxX = (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("x");
		chartConfigCasesByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigCasesByTime.setyAxisMin(0);
		int maxY = getMaxValueFromListOfXYMaps(chartDataCasesByTime.get(0));
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		chartConfigCasesByTime.setyAxisMax(maxY / factor * factor + factor);
		chartConfigCasesByTime.setyAxisInterval(factor);

		chartConfigCasesByTime.setLegendHorizonalAlign("left");
		chartConfigCasesByTime.setLegendVerticalAlign("top");
		chartConfigCasesByTime.setDataSeries1Name("Total cases");
		chartConfigCasesByTime.setDataSeries2Name("4-day Moving Average of New Cases");
		
		DashboardChartConfig chartConfigRateOfChangeOfCases = new DashboardChartConfig(
				"Rate of Change of Cases in " + region, "Days Since Cases > 0", "Percent Change in New Cases",
				"scatter");
		chartConfigRateOfChangeOfCases.setyAxisNumberSuffix("%");
		chartConfigRateOfChangeOfCases.setxAxisPosition("bottom");
		chartConfigRateOfChangeOfCases.setxAxisLogarithmic("false");
		chartConfigRateOfChangeOfCases.setyAxisPosition("left");
		chartConfigRateOfChangeOfCases.setyAxisLogarithmic("false");
		chartConfigRateOfChangeOfCases.setShowLegend("true");
		chartConfigRateOfChangeOfCases.setDataPointSize(1);
		chartConfigRateOfChangeOfCases.setxGridDashType("dot");
		chartConfigRateOfChangeOfCases.setxAxisMin(0);
		maxX = (int) chartDataRateOfCasesByTime.get(0).get(chartDataRateOfCasesByTime.get(0).size() - 1).get("x");
		chartConfigRateOfChangeOfCases.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfigRateOfChangeOfCases.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(chartDataRateOfCasesByTime.get(0));
		maxY = maxY >= 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		chartConfigRateOfChangeOfCases.setyAxisMax(maxY / factor * factor + factor);
		chartConfigRateOfChangeOfCases.setyAxisInterval(factor);

		chartConfigRateOfChangeOfCases.setLegendHorizonalAlign("right");
		chartConfigRateOfChangeOfCases.setLegendVerticalAlign("top");
		chartConfigRateOfChangeOfCases.setDataSeries1Name("% Change in Cases");
		chartConfigRateOfChangeOfCases.setDataSeries2Name("4-day Moving Average");
		
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
		
		////////// CHART CONFIGURATION - DEATHS ///////////
		log.info("Configuring all the charts for DEATHS...");
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
		chartConfigDeathsByTime.setyAxisMax(maxY / factor * factor + factor);
		chartConfigDeathsByTime.setyAxisInterval(factor);

		chartConfigDeathsByTime.setLegendHorizonalAlign("left");
		chartConfigDeathsByTime.setLegendVerticalAlign("top");
		chartConfigDeathsByTime.setDataSeries1Name("Total Deaths");
		chartConfigDeathsByTime.setDataSeries2Name("4-day Moving Average of New Deaths");
		
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
		
		//////// WRITE TO DASHBOARD CONFIGURATION LIST ////////
		log.info("Writing all the configurations to the dashboardList...");
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataCasesByTime), chartConfigCasesByTime));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataRateOfCasesByTime), chartConfigRateOfChangeOfCases));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataAccelOfCasesByTime), chartConfigAccelerationOfCases));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataChangeOfCasesByCases), chartConfigRateOfCasesVersusCases));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataDeathsByTime), chartConfigDeathsByTime));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataRateOfDeathsByTime), chartConfigRateOfChangeOfDeaths));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataAccelOfDeathsByTime), chartConfigAccelerationOfDeaths));
		dashboardList.add(new Dashboard(new DashboardChartData(chartDataChangeOfDeathsByDeaths), chartConfigRateOfDeathsVersusDeaths));

		return dashboardList;
	}

	@Override
	public void makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats) {
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY U.S. TOTALS");
		log.info("Getting U.S. data for populating By U.S. Totals row of dashboard...");
		List<UnitedStatesData> usaData = Regions.USA.getCoronaVirusDataFromExternalSource(dataFactory.getExternalDataService(Regions.USA.name()));
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
		dashStats.setProportionOfRegionPopToUsPop(regionPopulation * 100.0 / Regions.USA.getRegionData().getPopulation());
	}

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
