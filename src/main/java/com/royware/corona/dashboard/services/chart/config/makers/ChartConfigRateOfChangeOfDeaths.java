package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigRateOfChangeOfDeaths implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
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
		maxX = (int) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartList.get(0));
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
}
