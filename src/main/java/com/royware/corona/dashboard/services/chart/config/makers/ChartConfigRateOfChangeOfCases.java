package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.ChartListConstants;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigRateOfChangeOfCases implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartTitle("Rate of Change of Positives in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Percent Change in New Positives");
		chartConfig.setDataSeries1Name("% Change in Positives");
		chartConfig.setDataSeries2Name(ChartListConstants.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");

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
		maxX = (int) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartList.get(0));
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
}
