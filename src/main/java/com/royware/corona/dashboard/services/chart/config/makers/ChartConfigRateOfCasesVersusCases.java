package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigRateOfCasesVersusCases implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
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
		int cases = (Integer) chartList.get(0).get(0).get("x");
		cases = (cases <= 0) ? 1 : cases;
		int exp = (int) Math.log10(cases);
		chartConfig.setxAxisMin((int) Math.pow(10, exp));
		cases = (Integer) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		cases = (cases <= 0) ? 1 : cases;
		exp = (int) Math.log10(cases);
		chartConfig.setxAxisMax((int) Math.pow(10, 1 + (int) Math.log10(cases)));
		Double minValue = (Double) chartList.get(0).get(0).get("y");
		int digits = minValue > 0 ? (int) Math.log10((Double) chartList.get(0).get(0).get("y")) : 1;
		chartConfig.setyAxisMin((int) Math.pow(10, digits));
		chartConfig.setyAxisMax((int) Math.pow(10,
				1 + (int) Math.log10((double) chartList.get(1).get(1).get("y"))));

		chartConfig.setLegendHorizonalAlign("left");
		chartConfig.setLegendVerticalAlign("top");
		return chartConfig;
	}
}
