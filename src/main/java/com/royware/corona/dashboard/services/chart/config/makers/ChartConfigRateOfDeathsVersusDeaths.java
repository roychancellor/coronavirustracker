package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.interfaces.charts.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigRateOfDeathsVersusDeaths implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
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
		cases = (Integer) chartList.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMax((int) Math.pow(10, 1 + exp));
		minValue = Double.valueOf(ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartList.get(0)));
		exp = minValue > 0 ? (int) Math.log10(minValue) : 0;
		chartConfig.setyAxisMin((int) Math.pow(10, exp));
		double maxValue = (double) chartList.get(1).get(1).get("y");
		maxValue = maxValue > 0 ? maxValue : 1;
		chartConfig.setyAxisMax((int) Math.pow(10, 1 + (int) Math.log10(maxValue)));

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}
}
