package com.royware.corona.dashboard.services.chart.config.makers;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.chartconfig.IChartConfigMaker;
import com.royware.corona.dashboard.model.dashboard.DashboardChartConfig;

public class ChartConfigTotalCurrentCases implements IChartConfigMaker {

	@Override
	public DashboardChartConfig makeConfigFrom(List<List<Map<Object, Object>>> chartList, String region) {
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
		int maxX = (int) chartList.get(0).get(chartList.get(0).size() - 1).get("x");
		chartConfig.setxAxisMax(maxX / 10 * 10 + (int) Math.pow(10, (int)Math.log10(maxX) - 1));
		chartConfig.setyAxisMin(0);
		int maxY = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartList.get(0));
		int maxY2 = ChartConfigMakerUtilities.getMaxValueFromListOfXYMaps(chartList.get(1));
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
}
