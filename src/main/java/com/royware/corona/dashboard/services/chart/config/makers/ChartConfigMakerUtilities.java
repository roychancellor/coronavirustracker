package com.royware.corona.dashboard.services.chart.config.makers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.royware.corona.dashboard.enums.charts.ChartScalingConstants;

public class ChartConfigMakerUtilities {
	private static final Logger log = LoggerFactory.getLogger(ChartConfigMakerUtilities.class);

	public static int computeTotalQuantityLastN(List<Map<Object, Object>> dataList, int lastN) {
		log.debug("Computing total quantity last " + lastN + " days");
		if(dataList.size() < lastN + 1) {
			return 0;
		}
		
		int sum = 0;
		int qtyToday = 0;
		int qtyYesterday = 0;
		for(int n = dataList.size() - 1; n > dataList.size() - lastN - 1; n--) {
			qtyToday = (int)dataList.get(n).get("y");
			qtyYesterday = (int)dataList.get(n - 1).get("y");
			sum += qtyToday - qtyYesterday;
		}
		log.debug("sum =  " + sum);
		return sum;
	}
	
	public static int getMinValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		log.debug("Computing minimum value from list of XY maps");
		int dayThreshold = ChartScalingConstants.DAYS_THRESHOLD_FOR_Y_MAX.getValue();
		if(dataList.size() < dayThreshold) {
			dayThreshold = dataList.size() / 5;
		}
		
		Double min = Double.valueOf(dataList.get(dayThreshold).get("y").toString());

		for (Map<Object, Object> xy : dataList) {
			if (Double.valueOf(xy.get("x").toString()) > dayThreshold && Double.valueOf(xy.get("y").toString()) < min) {
				min = Double.valueOf(xy.get("y").toString());
			}
		}
		if (min.isNaN() || min.isInfinite()) {
			min = 0.0;
		}
		log.debug("min =  " + min);
		return min.intValue();
	}

	public static int getMaxValueFromListOfXYMaps(List<Map<Object, Object>> dataList) {
		log.debug("Computing maximum value from list of XY maps");
		int dayThreshold = ChartScalingConstants.DAYS_THRESHOLD_FOR_Y_MAX.getValue();
		if(dataList.size() < dayThreshold) {
			dayThreshold = dataList.size() / 5;
		}
		
		Double max = Double.valueOf(dataList.get(dayThreshold).get("y").toString());

		for (Map<Object, Object> xy : dataList) {
			if (Double.valueOf(xy.get("x").toString()) > dayThreshold && Double.valueOf(xy.get("y").toString()) > max) {
				max = Double.valueOf(xy.get("y").toString());
			}
		}
		if (max.isNaN() || max.isInfinite()) {
			max = 99.0;
		}
		log.debug("max =  " + max);
		return max.intValue();
	}
}
