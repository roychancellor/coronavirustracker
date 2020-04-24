package com.royware.corona.dashboard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartService;
import com.royware.corona.dashboard.interfaces.DashboardConfigService;
import com.royware.corona.dashboard.model.Dashboard;
import com.royware.corona.dashboard.model.DashboardChartConfig;
import com.royware.corona.dashboard.model.DashboardChartData;
import com.royware.corona.dashboard.model.DashboardStatistics;

@Service
public class DashboardConfigServiceImpl implements DashboardConfigService {
	@Autowired
	ChartService chartService;

	@Override
	public <T extends CanonicalCases> List<Dashboard> makeAllDashboardCharts(List<T> caseList, String region, DashboardStatistics dashStats) {
		List<Dashboard> dashboardList = new ArrayList<>();
		List<List<Map<Object, Object>>> dataCasesByTime = chartService
				.getTotalCasesVersusTimeWithExponentialFit(caseList);
		List<List<Map<Object, Object>>> dataRateOfCasesByTime = chartService
				.getDailyRateOfChangeOfCasesWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataAccelOfCasesByTime = chartService
				.getDailyAccelerationOfCasesWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataChangeOfCasesByCases = chartService
				.getChangeInTotalCasesVersusCaseswithExponentialLine(caseList);
		List<List<Map<Object, Object>>> dataDeathsByTime = chartService
				.getTotalDeathsVersusTimeWithExponentialFit(caseList);
		List<List<Map<Object, Object>>> dataRateOfDeathsByTime = chartService
				.getDailyRateOfChangeOfDeathsWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataAccelOfDeathsByTime = chartService
				.getDailyAccelerationOfDeathsWithMovingAverage(caseList);
		List<List<Map<Object, Object>>> dataChangeOfDeathsByDeaths = chartService
				.getChangeInTotalDeathsVersusDeathsWithExponentialLine(caseList);

		// Set all the individual dashboard statistics
		dashStats.setCasesTotal((int) dataCasesByTime.get(0).get(dataCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setCasesToday((int) dataCasesByTime.get(0).get(dataCasesByTime.get(0).size() - 1).get("y")
				- (int) dataCasesByTime.get(0).get(dataCasesByTime.get(0).size() - 2).get("y"));
		dashStats.setRateOfCasesToday(
				(double) dataRateOfCasesByTime.get(0).get(dataRateOfCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setAccelOfCasesToday(
				(double) dataAccelOfCasesByTime.get(0).get(dataAccelOfCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setDeathsTotal(
				(int) dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.get(0).size() - 1).get("x"));
		dashStats.setDeathsToday((int) dataChangeOfDeathsByDeaths.get(0)
				.get(dataChangeOfDeathsByDeaths.get(0).size() - 1).get("x")
				- (int) dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.get(0).size() - 2).get("x"));
		dashStats.setRateOfDeathsToday(
				(double) dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.get(0).size() - 1).get("y"));
		dashStats.setAccelOfDeathsToday(
				((double) dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.get(0).size() - 1).get("y")
				/ (double) dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.get(0).size() - 2).get("y") - 1)
				* 100.0
		);

		///// Configure all the dashboards individually
		DashboardChartConfig configCasesByTime = new DashboardChartConfig("Total Cases in " + region,
				"Days Since Cases > 0", "Total Cases", "scatter");
		configCasesByTime.setyAxisNumberSuffix("");
		configCasesByTime.setxAxisPosition("bottom");
		configCasesByTime.setxAxisLogarithmic("false");
		configCasesByTime.setyAxisPosition("left");
		configCasesByTime.setyAxisLogarithmic("false");
		configCasesByTime.setShowLegend("true");
		configCasesByTime.setDataPointSize(1);
		configCasesByTime.setxGridDashType("dot");
		configCasesByTime.setxAxisMin(0);
		int maxX = (int) dataCasesByTime.get(0).get(dataCasesByTime.get(0).size() - 1).get("x");
		configCasesByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		configCasesByTime.setyAxisMin(0);
		int maxY = getMaxValueFromListOfXYMaps(dataCasesByTime.get(0));
		int factor = (int) Math.pow(10, (int) Math.log10(maxY));
		configCasesByTime.setyAxisMax(maxY / factor * factor + factor);
		configCasesByTime.setyAxisInterval(factor);
//			log.info("1: factor = " + factor + ", maxY = " + maxY + ", yAxisMax = " + configCasesByTime.getyAxisMax());

		configCasesByTime.setLegendHorizonalAlign("left");
		configCasesByTime.setLegendVerticalAlign("top");
		configCasesByTime.setDataSeries1Name("Total cases");
		configCasesByTime.setDataSeries2Name("4-day Moving Average of New Cases");
		
		DashboardChartConfig rateOfChangeOfCasesChartConfig = new DashboardChartConfig(
				"Rate of Change of Cases in " + region, "Days Since Cases > 0", "Percent Change in New Cases",
				"scatter");
		rateOfChangeOfCasesChartConfig.setyAxisNumberSuffix("%");
		rateOfChangeOfCasesChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfCasesChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setyAxisPosition("left");
		rateOfChangeOfCasesChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfCasesChartConfig.setShowLegend("true");
		rateOfChangeOfCasesChartConfig.setDataPointSize(1);
		rateOfChangeOfCasesChartConfig.setxGridDashType("dot");
		rateOfChangeOfCasesChartConfig.setxAxisMin(0);
		maxX = (int) dataRateOfCasesByTime.get(0).get(dataRateOfCasesByTime.get(0).size() - 1).get("x");
		rateOfChangeOfCasesChartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		rateOfChangeOfCasesChartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(dataRateOfCasesByTime.get(0));
//			log.info("rate of cases, maxY = " + maxY);
		maxY = maxY > 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		rateOfChangeOfCasesChartConfig.setyAxisMax(maxY / factor * factor + factor);
		rateOfChangeOfCasesChartConfig.setyAxisInterval(factor);

		rateOfChangeOfCasesChartConfig.setLegendHorizonalAlign("right");
		rateOfChangeOfCasesChartConfig.setLegendVerticalAlign("top");
		rateOfChangeOfCasesChartConfig.setDataSeries1Name("% Change in Cases");
		rateOfChangeOfCasesChartConfig.setDataSeries2Name("4-day Moving Average");
		
		DashboardChartConfig accelerationOfCasesChartConfig = new DashboardChartConfig(
				"Acceleration of Cases in " + region, "Days Since Cases > 0", "Percent Change in the Rate of New Cases",
				"scatter");
		accelerationOfCasesChartConfig.setyAxisNumberSuffix("%");
		accelerationOfCasesChartConfig.setxAxisPosition("bottom");
		accelerationOfCasesChartConfig.setxAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setyAxisPosition("left");
		accelerationOfCasesChartConfig.setyAxisLogarithmic("false");
		accelerationOfCasesChartConfig.setShowLegend("true");
		accelerationOfCasesChartConfig.setDataPointSize(1);
		accelerationOfCasesChartConfig.setxGridDashType("dot");
		accelerationOfCasesChartConfig.setxAxisMin(0);
		maxX = (int) dataAccelOfCasesByTime.get(0).get(dataAccelOfCasesByTime.get(0).size() - 1).get("x");
		accelerationOfCasesChartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		int minY = getMinValueFromListOfXYMaps(dataAccelOfCasesByTime.get(0));
		maxY = getMaxValueFromListOfXYMaps(dataAccelOfCasesByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		minY = minY < -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		accelerationOfCasesChartConfig.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		accelerationOfCasesChartConfig.setyAxisInterval(factor);
		accelerationOfCasesChartConfig.setyAxisMax(maxY / factor * factor + factor);

		accelerationOfCasesChartConfig.setLegendHorizonalAlign("right");
		accelerationOfCasesChartConfig.setLegendVerticalAlign("top");
		accelerationOfCasesChartConfig.setDataSeries1Name("Acceleration of Cases");
		accelerationOfCasesChartConfig.setDataSeries2Name("4-day Moving Average");
		
		DashboardChartConfig rateOfCasesVersusCasesChartConfig = new DashboardChartConfig(
				"Detecting Inflection of Cases in " + region, "Total Cases", "Daily Change in Total Cases", "scatter");
		rateOfCasesVersusCasesChartConfig.setyAxisNumberSuffix("");
		rateOfCasesVersusCasesChartConfig.setxAxisPosition("bottom");
		rateOfCasesVersusCasesChartConfig.setxAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setyAxisPosition("left");
		rateOfCasesVersusCasesChartConfig.setyAxisLogarithmic("true");
		rateOfCasesVersusCasesChartConfig.setShowLegend("true");
		rateOfCasesVersusCasesChartConfig.setDataPointSize(1);
		rateOfCasesVersusCasesChartConfig.setxGridDashType("dot");
		int cases = (Integer) dataChangeOfCasesByCases.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		int exp = (int) Math.log10(cases);
		rateOfCasesVersusCasesChartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) dataChangeOfCasesByCases.get(0).get(dataChangeOfCasesByCases.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		rateOfCasesVersusCasesChartConfig.setxAxisMax((int) Math.pow(10, 1 + (int) Math.log10(cases)));
		Double minValue = (Double) dataChangeOfCasesByCases.get(0).get(0).get("y");
		int digits = minValue > 0 ? (int) Math.log10((Double) dataChangeOfCasesByCases.get(0).get(0).get("y")) : 1;
		rateOfCasesVersusCasesChartConfig.setyAxisMin((int) Math.pow(10, digits));
		rateOfCasesVersusCasesChartConfig.setyAxisMax((int) Math.pow(10,
				1 + (int) Math.log10((double) getMaxValueFromListOfXYMaps(dataChangeOfCasesByCases.get(1)))));

		rateOfCasesVersusCasesChartConfig.setLegendHorizonalAlign("left");
		rateOfCasesVersusCasesChartConfig.setLegendVerticalAlign("top");
		rateOfCasesVersusCasesChartConfig.setDataSeries1Name("Daily change in cases");
		rateOfCasesVersusCasesChartConfig.setDataSeries2Name("Pure exponential (k = 0.3)");
		
		DashboardChartConfig configDeathsByTime = new DashboardChartConfig("Total Deaths in " + region,
				"Days Since Deaths > 0", "Total Deaths", "scatter");
		configDeathsByTime.setyAxisNumberSuffix("");
		configDeathsByTime.setxAxisPosition("bottom");
		configDeathsByTime.setxAxisLogarithmic("false");
		configDeathsByTime.setyAxisPosition("left");
		configDeathsByTime.setyAxisLogarithmic("false");
		configDeathsByTime.setShowLegend("true");
		configDeathsByTime.setDataPointSize(1);
		configDeathsByTime.setxGridDashType("dot");
		configDeathsByTime.setxAxisMin(0);
		maxX = (int) dataDeathsByTime.get(0).get(dataDeathsByTime.get(0).size() - 1).get("x");
		configDeathsByTime.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		configDeathsByTime.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(dataDeathsByTime.get(0));
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		configDeathsByTime.setyAxisMax(maxY / factor * factor + factor);
		configDeathsByTime.setyAxisInterval(factor);
//			log.info("1: factor = " + factor + ", maxY = " + maxY + ", yAxisMax = " + configDeathsByTime.getyAxisMax());

		configDeathsByTime.setLegendHorizonalAlign("left");
		configDeathsByTime.setLegendVerticalAlign("top");
		configDeathsByTime.setDataSeries1Name("Total Deaths");
		configDeathsByTime.setDataSeries2Name("4-day Moving Average of New Deaths");
		
		DashboardChartConfig rateOfChangeOfDeathsChartConfig = new DashboardChartConfig(
				"Rate of Change of Deaths in " + region, "Days Since Deaths > 0", "Percent Change in New Deaths",
				"scatter");
		rateOfChangeOfDeathsChartConfig.setyAxisNumberSuffix("%");
		rateOfChangeOfDeathsChartConfig.setxAxisPosition("bottom");
		rateOfChangeOfDeathsChartConfig.setxAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setyAxisPosition("left");
		rateOfChangeOfDeathsChartConfig.setyAxisLogarithmic("false");
		rateOfChangeOfDeathsChartConfig.setShowLegend("true");
		rateOfChangeOfDeathsChartConfig.setDataPointSize(1);
		rateOfChangeOfDeathsChartConfig.setxAxisGridlinesDisplay("dot");
		rateOfChangeOfDeathsChartConfig.setxAxisMin(0);
		maxX = (int) dataRateOfDeathsByTime.get(0).get(dataRateOfDeathsByTime.get(0).size() - 1).get("x");
		rateOfChangeOfDeathsChartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		rateOfChangeOfDeathsChartConfig.setyAxisMin(0);
		maxY = getMaxValueFromListOfXYMaps(dataRateOfDeathsByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		rateOfChangeOfDeathsChartConfig.setyAxisInterval(factor);
		rateOfChangeOfDeathsChartConfig.setyAxisMax(maxY / factor * factor + factor);

		rateOfChangeOfDeathsChartConfig.setLegendHorizonalAlign("right");
		rateOfChangeOfDeathsChartConfig.setLegendVerticalAlign("top");
		rateOfChangeOfDeathsChartConfig.setDataSeries1Name("% change in deaths");
		rateOfChangeOfDeathsChartConfig.setDataSeries2Name("4-day Moving Average");
		
		DashboardChartConfig accelerationOfDeathsChartConfig = new DashboardChartConfig(
				"Acceleration of Deaths in " + region, "Days Since Deaths > 0", "Percent Change in the Rate of New Deaths",
				"scatter");
		accelerationOfDeathsChartConfig.setyAxisNumberSuffix("%");
		accelerationOfDeathsChartConfig.setxAxisPosition("bottom");
		accelerationOfDeathsChartConfig.setxAxisLogarithmic("false");
		accelerationOfDeathsChartConfig.setyAxisPosition("left");
		accelerationOfDeathsChartConfig.setyAxisLogarithmic("false");
		accelerationOfDeathsChartConfig.setShowLegend("true");
		accelerationOfDeathsChartConfig.setDataPointSize(1);
		accelerationOfDeathsChartConfig.setxGridDashType("dot");
		accelerationOfDeathsChartConfig.setxAxisMin(0);
		maxX = (int) dataAccelOfDeathsByTime.get(0).get(dataAccelOfDeathsByTime.get(0).size() - 1).get("x");
		accelerationOfDeathsChartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int) Math.log10(maxX)));
		minY = getMinValueFromListOfXYMaps(dataAccelOfDeathsByTime.get(0));
		maxY = getMaxValueFromListOfXYMaps(dataAccelOfDeathsByTime.get(0));
		maxY = maxY > 100 ? 99 : maxY;
		minY = minY < -100 ? -99 : minY;
		factor = (int) Math.pow(10, (int) Math.log10(minY));
		if (factor == 0) {
			factor = 10;
		}
		accelerationOfDeathsChartConfig.setyAxisMin(minY / factor * factor - factor);
		factor = (int) Math.pow(10, (int) Math.log10(maxY));
		if (factor == 0) {
			factor = 10;
		}
		accelerationOfDeathsChartConfig.setyAxisInterval(factor);
		accelerationOfDeathsChartConfig.setyAxisMax(maxY / factor * factor + factor);

		accelerationOfDeathsChartConfig.setLegendHorizonalAlign("right");
		accelerationOfDeathsChartConfig.setLegendVerticalAlign("top");
		accelerationOfDeathsChartConfig.setDataSeries1Name("Acceleration of Deaths");
		accelerationOfDeathsChartConfig.setDataSeries2Name("4-day Moving Average");
		
		DashboardChartConfig rateOfDeathsVersusDeathsChartConfig = new DashboardChartConfig(
				"Detecting Inflection of Deaths in " + region, "Total Deaths", "Daily Change in Total Deaths",
				"scatter");
		rateOfDeathsVersusDeathsChartConfig.setyAxisNumberSuffix("");
		rateOfDeathsVersusDeathsChartConfig.setxAxisPosition("bottom");
		rateOfDeathsVersusDeathsChartConfig.setxAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setyAxisPosition("left");
		rateOfDeathsVersusDeathsChartConfig.setyAxisLogarithmic("true");
		rateOfDeathsVersusDeathsChartConfig.setShowLegend("true");
		rateOfDeathsVersusDeathsChartConfig.setDataPointSize(1);
		rateOfDeathsVersusDeathsChartConfig.setxGridDashType("dot");
		cases = (Integer) dataChangeOfDeathsByDeaths.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		rateOfDeathsVersusDeathsChartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) dataChangeOfDeathsByDeaths.get(0).get(dataChangeOfDeathsByDeaths.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		rateOfDeathsVersusDeathsChartConfig.setxAxisMax((int) Math.pow(10, 1 + exp));
		minValue = Double.valueOf(getMinValueFromListOfXYMaps(dataChangeOfDeathsByDeaths.get(0))); // (Double)dataChangeOfDeathsByDeaths.get(0).get(0).get("y");
		exp = minValue > 0 ? (int) Math.log10(minValue) : 0;
		rateOfDeathsVersusDeathsChartConfig.setyAxisMin((int) Math.pow(10, exp));
		double maxValue = (double) getMaxValueFromListOfXYMaps(dataChangeOfDeathsByDeaths.get(1));
		maxValue = maxValue > 0 ? maxValue : 1;
		rateOfDeathsVersusDeathsChartConfig.setyAxisMax((int) Math.pow(10, 1 + (int) Math.log10(maxValue)));

		rateOfDeathsVersusDeathsChartConfig.setLegendHorizonalAlign("left");
		rateOfDeathsVersusDeathsChartConfig.setLegendVerticalAlign("top");
		rateOfDeathsVersusDeathsChartConfig.setDataSeries1Name("Daily change in deaths");
		rateOfDeathsVersusDeathsChartConfig.setDataSeries2Name("Pure exponential (k = 0.3)");
		
		dashboardList.add(new Dashboard(new DashboardChartData(dataCasesByTime), configCasesByTime));
		dashboardList.add(new Dashboard(new DashboardChartData(dataRateOfCasesByTime), rateOfChangeOfCasesChartConfig));
		dashboardList.add(new Dashboard(new DashboardChartData(dataAccelOfCasesByTime), accelerationOfCasesChartConfig));
		dashboardList.add(new Dashboard(new DashboardChartData(dataChangeOfCasesByCases), rateOfCasesVersusCasesChartConfig));
		dashboardList.add(new Dashboard(new DashboardChartData(dataDeathsByTime), configDeathsByTime));
		dashboardList.add(new Dashboard(new DashboardChartData(dataRateOfDeathsByTime), rateOfChangeOfDeathsChartConfig));
		dashboardList.add(new Dashboard(new DashboardChartData(dataAccelOfDeathsByTime), accelerationOfDeathsChartConfig));
		dashboardList.add(new Dashboard(new DashboardChartData(dataChangeOfDeathsByDeaths), rateOfDeathsVersusDeathsChartConfig));

		return dashboardList;
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
//			log.info("getMaxValueFromListOfXYMaps, max = " + max);
		if (max.isNaN() || max.isInfinite()) {
			max = 99.0;
		}
		return max.intValue();
	}
}
