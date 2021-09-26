package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.DataTransformConstants;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigAccelerationOfCases implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
		int maxX;
		int maxY;
		int factor;
		DashboardChartConfig chartConfig = new DashboardChartConfig();
		
		chartConfig.setChartTitle("Acceleration of Positives in " + region);
		chartConfig.setxAxisTitle("Days Since Cases > 0");
		chartConfig.setyAxisTitle("Percent Change in Rate of New Positives");
		chartConfig.setDataSeries1Name("Acceleration of Positives");
		chartConfig.setDataSeries2Name(DataTransformConstants.MOVING_AVERAGE_SIZE.getValue() + "-day Moving Average");

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
		int minY = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartList.get(0));
		int minYMovAvg = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartList.get(1));
		minY = (minY < minYMovAvg) ? minY : minYMovAvg;
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartList.get(0));
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
}
