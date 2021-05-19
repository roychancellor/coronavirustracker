package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigAccelerationOfDeaths implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
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
		maxX = (int) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		minY = ChartConfigMakerUtilities.getMinValueFromListOfXYMaps(chartList.get(0));
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
